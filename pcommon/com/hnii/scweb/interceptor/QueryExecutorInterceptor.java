package com.hnii.scweb.interceptor;

import java.net.URLDecoder;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;
import com.hnii.scweb.util.BaseBean;
import com.hnii.scweb.util.FastJson;
import com.hnii.scweb.util.StringUtil;

@Intercepts({ @Signature(type = Executor.class, method = "query", args = {
		MappedStatement.class, Object.class, RowBounds.class,
		ResultHandler.class }) })
public class QueryExecutorInterceptor implements Interceptor {
	Logger logger = Logger.getLogger(QueryExecutorInterceptor.class);

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		if (invocation.getArgs()[1] instanceof Map) {// 拦截查询参数
			JSONObject map = JSONObject.parseObject(FastJson
					.bean2Json(invocation.getArgs()[1]));

			if (map != null && map.entrySet() != null) {
				for (Entry<String, Object> entry : map.entrySet()) {
					if (!(map.get(entry.getKey()) instanceof List)) {// 数据库查询parameterType="java.util.list"的情境不进行转换
						if (entry != null && entry.getValue() != null) {
							// 中文转码
							entry.setValue(URLDecoder.decode(entry.getValue()
									.toString(), "utf-8"));
							// 查询参数包含%
							if (entry.getValue() instanceof String
									&& entry.getValue().toString()
											.contains("%")) {
								logger.info(entry.getKey() + ":"
										+ entry.getValue());
								entry.setValue(entry.getValue().toString()
										.replaceAll("%", "\\\\%"));
								logger.info("replacelater-----------"
										+ entry.getKey() + ":"
										+ entry.getValue());
							}
						}

					}

				}
			}

			logger.info("json:" + map.toJSONString());
			invocation.getArgs()[1] = FastJson.json2Bean(map.toJSONString(),
					invocation.getArgs()[1].getClass());
			logger.info("invocation.getArgs()[1]:"
					+ FastJson.bean2Json(invocation.getArgs()[1]));
		} else if (invocation.getArgs()[1] instanceof String) {
			invocation.getArgs()[1].toString().replaceAll("%", "\\\\%");
		}

		return invocation.proceed();
	}

	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	@Override
	public void setProperties(Properties properties) {
	}
}
