//package java_ws_demo.wsdemo.interceptor;
//
//import com.supervisionProj.supervision.service.UserTokenService;
//import com.supervisionProj.supervision.utils.CConfig;
//import com.supervisionProj.supervision.utils.JsonUtil;
//import com.supervisionProj.supervision.utils.MapUtil;
//import org.springframework.web.context.WebApplicationContext;
//import org.springframework.web.context.support.WebApplicationContextUtils;
//import org.springframework.web.servlet.HandlerInterceptor;
//
//import javax.annotation.Resource;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//import java.util.Enumeration;
//import java.util.Map;
//
///**
// * Created by Ty on 2019/2/21.
// */
//public class LoginInterceptor implements HandlerInterceptor {
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
////        String origin = request.getHeader("Origin");
////        response.setHeader("Access-Control-Allow-Origin", origin);
////        response.setHeader("Access-Control-Allow-Methods", "*");
////        response.setHeader("Access-Control-Allow-Headers", "Origin,Content-Type,Accept,token,X-Requested-With");
////        response.setHeader("Access-Control-Allow-Credentials", "true");
////        String sessionUuid = AppCookie.readSession();
////        if (STR.isBlank("s")) {
////            System.out.println("当前用户未登录");
////            response.setStatus(401);
////            response.setContentType("application/json; charset=UTF-8");
////            response.getWriter().print("{\"error_desc\":\"身份过期，请重新登录\"}");
////            return false;
////        }
////        System.out.println("当前用户已登录");
////        System.out.println("session uuid:" + sessionUuid);
//        String token = request.getHeader("token");
//        Enumeration<String> headerNames = request.getHeaderNames();
//        while(headerNames.hasMoreElements()){
//            System.out.println("header name :"+headerNames.nextElement());
//        }
//
//
//
//
//        // session是否存在
//        HttpSession session = request.getSession();
//        Map accountMap = (Map) session.getAttribute(CConfig.SESSION_KEY);
//        if (accountMap == null) {
//            if (token != null ) {
//                System.out.println("token value :"+token);
//                boolean b = userTokenService.checkTokenExist(token);
//                System.out.println(b);
//                if(b){
//                    userTokenService.initSessionByToken(session, token);
//                }
//                return true;
//            } else {
//                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//                response.setContentType("application/json;charset=utf-8");
//                response.getWriter().write(JsonUtil.mapToJsonStr(new MapUtil().add("error", "session-died")));
//                return false;
//            }
//        }
//        return true;
//    }
//
//    private <T> T getService(Class<T> clazz, HttpServletRequest request) {
//        //通过该方法获得的applicationContext 已经是初始化之后的applicationContext 容器
//        WebApplicationContext applicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
//        return applicationContext.getBean(clazz);
//    }
//
////    @Override
////    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
////        //表示允许哪些原始域进行跨域访问。
////        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
////        //表示允许发起跨域请求的方式，例如GET/POST。
////        response.setHeader("Access-Control-Allow-Methods", "*");
////        //表示允许发起跨域请求的额外头信息。
////        response.setHeader("Access-Control-Allow-Headers", "Origin,Content-Type,Accept,token,X-Requested-With");
////        //表示是否允许客户端获取用户凭据。
////        response.setHeader("Access-Control-Allow-Credentials", "true");
////
////        if (request.getMethod().equals(RequestMethod.OPTIONS.name())) {
////            response.setStatus(HttpStatus.OK.value());
////            return false;
////        }
////        String token = request.getHeader("token");
////        // session是否存在
////        HttpSession session = request.getSession();
////        Map accountMap = (Map) session.getAttribute(CConfig.SESSION_KEY);
////        if (accountMap == null) {
////            if (token != null && userTokenService.checkTokenExist(token)) {
////                userTokenService.initSessionByToken(session, token);
////                return true;
////            } else {
////                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
////                response.setContentType("application/json;charset=utf-8");
////                response.getWriter().write(JsonUtil.mapToJsonStr(new MapUtil().add("error", "session-died")));
////                return false;
////            }
////        }
////
////        // 检验权限
////        WebApplicationContext applicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
////        UserServiceImpl userService = applicationContext.getBean(UserServiceImpl.class);
////        List<Integer> powerCodeList = userService.findPermissionIdListByUserId((Integer) accountMap.get("id"));
////        HandlerMethod handlerMethod = (HandlerMethod) handler;
////        RequiredPermissions requiredPermissions = getRequiredPermissionsFromHandlerMethodWithCache(handlerMethod);
////        if (requiredPermissions == null) {
////            return true;
////        }
////        if (powerCodeList == null) {
////            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
////            response.setContentType("application/json;charset=utf-8");
////            response.getWriter().write(JsonUtil.mapToJsonStr(new MapUtil().add("error", "no permission")));
////            return false;
////        }
////        int[] requirePowerCodes = requiredPermissions.value();
////        boolean res = isPermitted(requirePowerCodes, powerCodeList);
////        if (!res) {
////            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
////            response.setContentType("application/json;charset=utf-8");
////            response.getWriter().write(JsonUtil.mapToJsonStr(new MapUtil().add("error", "no permission")));
////            return false;
////        }
////        return true;
////    }
////
////    private static String getHandlerMethodSignature(HandlerMethod handlerMethod) {
////        StringBuilder builder = new StringBuilder();
////        builder.append(handlerMethod.getBeanType().getCanonicalName());
////        builder.append(".");
////        builder.append(handlerMethod.getMethod().getName());
////        builder.append("(");
////        for (int i = 0; i < handlerMethod.getMethodParameters().length; ++i) {
////            MethodParameter methodParameter = handlerMethod.getMethodParameters()[i];
////            if (i > 0) builder.append(",");
////            builder.append(methodParameter.getParameterType().getCanonicalName());
////        }
////        builder.append(")");
////        return builder.toString();
////    }
////
////    private static final Map<String, Object> serviceMethodRequiredPermissionsMapping = new HashMap<>();
////    private static final Lock serviceMethodRequiredPermissionsLock = new ReentrantLock();
////
////    private RequiredPermissions getRequiredPermissionsFromHandlerMethodWithCache(HandlerMethod handlerMethod) {
////        return getAnnotationFromHandlerMethodWithCache(handlerMethod, RequiredPermissions.class, serviceMethodRequiredPermissionsMapping, serviceMethodRequiredPermissionsLock);
////    }
////
////    private <T extends Annotation> T getAnnotationFromHandlerMethodWithCache(HandlerMethod handlerMethod, Class<T> annotationCls, Map<String, Object> mapping, Lock lock) {
////        // 首先判断此方法是否有@annotationCls,并cache这个元信息避免重新判断
////        //这个函数是拿到方法签名
////        String signatureStr = getHandlerMethodSignature(handlerMethod);
////        Object methodCacheInfoObj;
////        //通过方法签名去map中拿 cacheinfo 缓存数据
////        methodCacheInfoObj = mapping.get(signatureStr);
////        //如果存在缓存对象，则检查这个缓存对象是不是annota cla的父类，如果是，则返回这个对象否则返回null
////        if (methodCacheInfoObj != null) {
////            if (annotationCls.isAssignableFrom(methodCacheInfoObj.getClass())) {
////                return (T) methodCacheInfoObj;
////            } else return null;
////        }
////
////        lock.lock();
////        //如果map中没有这个函数签名对应的对象，则进行检查，
////        try {
////            methodCacheInfoObj = mapping.get(signatureStr);
////            if (methodCacheInfoObj != null) {
//////                判定此 Class 对象所表示的类或接口与指定的 Class 参数所表示的类或接口是否相同，或是否是其超类或超接口。
//////                如果是则返回 true；否则返回 false。如果该 Class 表示一个基本类型，且指定的 Class 参数正是该 Class 对象，则该方法返回 true；否则返回 false。
//////        isAssignableFrom()方法与instanceof关键字的区别总结为以下两个点：
//////        isAssignableFrom()方法是从类继承的角度去判断，instanceof关键字是从实例继承的角度去判断。
//////        isAssignableFrom()方法是判断是否为某个类的父类，instanceof关键字是判断是否某个类的子类。
////                if (annotationCls.isAssignableFrom(methodCacheInfoObj.getClass())) {
////                    return (T) methodCacheInfoObj;
////                } else return null;
////            } else {
////                T anno = handlerMethod.getMethodAnnotation(annotationCls);
//////                主要问题在于不同的Annotation的拦截是不同的。 例如method级别的拦截。
//////                我们可以通过getMethodAnnotation（Class）的获取annotation。如果没有设置annotation，获取为空。
////                if (anno == null) {
////                    mapping.put(signatureStr, false);
////                    return null;
////                }
////                mapping.put(signatureStr, anno);
////                return anno;
////            }
////        } finally {
////            lock.unlock();
////        }
////    }
////
////    private boolean isPermitted(int[] permissions, List<Integer> curPermissions) {
////        for (int permission : permissions) {
////            if (!curPermissions.contains(permission)) {
////                return false;
////            }
////        }
////        return true;
////    }
//}
