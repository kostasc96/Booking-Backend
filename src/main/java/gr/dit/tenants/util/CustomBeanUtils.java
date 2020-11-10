package gr.dit.tenants.util;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Set;

public class CustomBeanUtils {
    public static void copyNonNullProperties(Object source, Object target) {
        BeanUtils.copyProperties(source, target, getNullPropertyNames(source));
    }

    public static void copyNonNullProperties(Object source, Object target, String... forcedIgnoreProperties) {
        BeanUtils.copyProperties(source, target,getNullAndIgnoredProperties(source,forcedIgnoreProperties));
    }

    private static String[] getNullAndIgnoredProperties(Object source, String... forcedIgnoreProperties)
    {
        String[] nullPropertyNames = getNullPropertyNames(source);
        int aLen = nullPropertyNames.length;
        int bLen = forcedIgnoreProperties.length;
        String[] result = new String[aLen + bLen];

        System.arraycopy(nullPropertyNames, 0, result, 0, aLen);
        System.arraycopy(nullPropertyNames, 0, result, aLen, bLen);

        return result;
    }


    private static String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<String>();
        for (PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null)
                emptyNames.add(pd.getName());
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }
}
