package cn.ruishan.main.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.log.Log;
import cn.ruishan.common.exception.BusinessException;
import cn.ruishan.common.security.LoginUser;
import cn.ruishan.common.security.OauthGrantedAuthority;
import cn.ruishan.common.security.Resource;
import cn.ruishan.iac.entity.*;
import cn.ruishan.iac.mapper.*;
import cn.ruishan.main.entity.BaseUser;
import cn.ruishan.main.entity.LoginType;
import cn.ruishan.main.mapper.BaseUserMapper;
import cn.ruishan.main.service.LoginUserDetailsService;
import cn.ruishan.sys.entity.IotCorp;
import cn.ruishan.sys.entity.SysUser;
import cn.ruishan.sys.mapper.IotCorpMapper;
import cn.ruishan.sys.mapper.SysUserMapper;
import cn.ruishan.sys.service.IIotCorpService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class LoginUserServiceImpl implements LoginUserDetailsService {

    private static Log log = Log.get(LoginUserServiceImpl.class);

    @Autowired
    private BaseUserMapper baseUserMapper;

    @Autowired
    private IacUserMapper iacUserMapper;

    @Autowired
    private EmsAuthMapper emsAuthMapper;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private IotCorpMapper iotCorpMapper;

    @Autowired
    private IacRelRoleResourceMapper iacRelRoleResourceMapper;

    @Autowired
    private IacRelRoleUserMapper iacRelRoleUserMapper;

    @Autowired
    private IacResourceMapper iacResourceMapper;

    @Override
    public LoginUser loadUserByUsername(String username) throws BusinessException {
        return this.loadUserByUsername(username, null, null);
    }

    @Override
    public LoginUser loadUserByUsername(String username, String loginType, String wxId) {

        LoginUser loginUser = new LoginUser();

        switch (loginType) {
            // 后台用户登陆
            case LoginType.SYS_ADMIN: {
                // 根据登录名获取用户
                BaseUser baseUser = baseUserMapper.selectOne(new QueryWrapper<BaseUser>().eq("loginname", username));
                if (BeanUtil.isEmpty(baseUser)) {
                    throw new InternalAuthenticationServiceException("用户名/登陆类型错误");
                }

                SysUser sysUser = sysUserMapper.selectById(baseUser.getUserId());
                if (BeanUtil.isEmpty(sysUser)){
                    throw new InternalAuthenticationServiceException("该用户不存在");
                }
                sysUser.buildUser(baseUser);

                loginUser.setUserId(sysUser.getUserId());
                loginUser.setUsername(sysUser.getUsername());
                loginUser.setLoginname(sysUser.getLoginname());
                loginUser.setPassword(sysUser.getPassword());
                loginUser.setStatus(false);
                // 将用户属性复制到登陆用户
                BeanUtil.copyProperties(sysUser, loginUser);

                loginUser.setAuthorities(CollUtil.newArrayList());
                loginUser.setResources(CollUtil.newArrayList());
            }
            break;
            case LoginType.IOT_CORP: {
                IotCorp iotCorp = iotCorpMapper.selectOne(new QueryWrapper<IotCorp>().eq("login_name", username));
                if (BeanUtil.isEmpty(iotCorp)) {
                    throw new InternalAuthenticationServiceException("用户名/登陆类型错误");
                }

                loginUser.setUserId(iotCorp.getCorpId());
                loginUser.setUsername(iotCorp.getName());
                loginUser.setLoginname(iotCorp.getLoginName());
                loginUser.setPassword(iotCorp.getPassword());
                loginUser.setStatus(iotCorp.getStatus());

                loginUser.setAuthorities(CollUtil.newArrayList());
                loginUser.setResources(CollUtil.newArrayList());
            }
            break;
            case LoginType.IAC_DEV:{
                EmsAuth emsAuth = emsAuthMapper.selectOne(new QueryWrapper<EmsAuth>().eq("serial_num",username));
                if (BeanUtil.isEmpty(emsAuth)) {
                    throw new InternalAuthenticationServiceException("用户名/登陆类型错误");
                }

                loginUser.setUserId(emsAuth.getId());
                loginUser.setUsername(username);
                loginUser.setLoginname(username);
                loginUser.setPassword(emsAuth.getUuid());
                loginUser.setSubstationId(emsAuth.getSubstationId());

                loginUser.setAuthorities(CollUtil.newArrayList());
                loginUser.setResources(CollUtil.newArrayList());
            }
            break;
            case LoginType.IAC_USR_WEB:{
                BaseUser baseUser = baseUserMapper.selectOne(new QueryWrapper<BaseUser>().eq("loginname", username));
                if (BeanUtil.isEmpty(baseUser)) {
                    throw new InternalAuthenticationServiceException("用户名/登陆类型错误");
                }

                IacUser iacUsr = iacUserMapper.selectById(baseUser.getUserId());
                if(BeanUtil.isEmpty(iacUsr)){
                    throw new InternalAuthenticationServiceException("用户不存在");
                }
                iacUsr.buildUser(baseUser);

                loginUser.setUserId(iacUsr.getUserId());
                loginUser.setUsername(iacUsr.getUsername());
                loginUser.setLoginname(iacUsr.getLoginname());
                loginUser.setPassword(iacUsr.getPassword());
                loginUser.setStatus(false);
                loginUser.setCorpId(iacUsr.getCorpId());

                List<OauthGrantedAuthority> authorities = CollUtil.newArrayList();
                List<Resource> resources = CollUtil.newArrayList();

                List<IacRelRoleUser> iacRelRoleUsers = iacRelRoleUserMapper.selectList(new LambdaQueryWrapper<IacRelRoleUser>().eq(IacRelRoleUser::getUserId,iacUsr.getUserId()));
                // 根据用户角色关联表获取角色ID列表
                Set<Integer> roleIds = CollUtil.newHashSet();
                if (CollUtil.isNotEmpty(iacRelRoleUsers)) {
                    for (IacRelRoleUser iacRelRoleUsr : iacRelRoleUsers) {
                        if (!BeanUtil.isEmpty(iacRelRoleUsr)) {
                            roleIds.add(iacRelRoleUsr.getRoleId());
                        }
                    }
                }

                List<IacRelRoleResource> iacRelRoleResources = iacRelRoleResourceMapper.selectList(new LambdaQueryWrapper<IacRelRoleResource>().in(IacRelRoleResource::getRoleId,roleIds));
                // 根据角色资源关联表获取资源ID列表
                Set<Integer> resourceIds = CollUtil.newHashSet();
                if (CollUtil.isNotEmpty(iacRelRoleResources)) {
                    for (IacRelRoleResource iacRelRoleResource : iacRelRoleResources) {
                        if (!BeanUtil.isEmpty(iacRelRoleResource)) {
                            resourceIds.add(iacRelRoleResource.getResourceId());
                        }
                    }
                }

                if (CollUtil.isNotEmpty(resourceIds)) {
                    List<IacResource> iacResources = iacResourceMapper.selectBatchIds(resourceIds);
                    for (IacResource iacResource : iacResources) {
                        OauthGrantedAuthority authority = new OauthGrantedAuthority();
                        if (StrUtil.isNotBlank(iacResource.getAuthority())) {
                            authority.setAuthority(iacResource.getAuthority());
                            authorities.add(authority);

                            Resource resource = new Resource();
                            BeanUtil.copyProperties(iacResource, resource);
                            resources.add(resource);
                        }
                    }
                }

                loginUser.setAuthorities(authorities);
                loginUser.setResources(resources);
            }
            default:
                break;
        }

        loginUser.setLoginType(loginType);
        return loginUser;
    }
}
