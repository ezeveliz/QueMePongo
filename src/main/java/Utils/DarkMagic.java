package Utils;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.http.NameValuePair;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DarkMagic {

	public static Object getField(Object obj, String fieldName) {
		try {
			Field field = FieldUtils.getField(obj.getClass(), fieldName, true);
			return field.get(obj);
		} catch (Exception e) {
			throw new RuntimeException("cannot get handlebars", e);
		}
	}

	/**
	 * Mapeo los parametros enviados como query string a un map con el que puedo trabajar
	 * @param pairs pares de clave - valor
	 * @return query string mapeado
	 */
	public static Map<String, String> toMap(List<NameValuePair> pairs){
		Map<String, String> map = new HashMap<>();
		for (NameValuePair pair : pairs) {
			map.put(pair.getName(), pair.getValue());
		}
		return map;
	}

}
