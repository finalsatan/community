package com.murphy.community.cache;

import com.murphy.community.dto.TagDTO;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * TagCache
 *
 * @author zhangyh2360@dimpt.com
 * @date 2020/2/11 11:03 下午
 */
public class TagCache {
    public static List<TagDTO> get() {
        List<TagDTO> tagDTOS = new ArrayList<>();
        TagDTO language = new TagDTO();
        language.setCategoryName("开发语言");
        language.setTags(Arrays.asList("js", "php", "java", "go", "python", "c", "c++"));
        tagDTOS.add(language);


        TagDTO framework = new TagDTO();
        framework.setCategoryName("平台框架");
        framework.setTags(Arrays.asList("spring", "spring boot", "django", "yii"));
        tagDTOS.add(framework);

        TagDTO server = new TagDTO();
        server.setCategoryName("服务器");
        server.setTags(Arrays.asList("linux", "nginx", "docker", "ubuntu"));
        tagDTOS.add(server);

        return tagDTOS;
    }

    public static String filterInvalid(String tags) {
        String[] splitTags = StringUtils.split(tags, ',');
        List<TagDTO> tagDTOS = get();
        List<String> tagList = tagDTOS.stream().flatMap(tag -> tag.getTags().stream()).collect(Collectors.toList());
        String invalidTags = Arrays.stream(splitTags).filter(t -> !tagList.contains(t)).collect(Collectors.joining(","));
        return invalidTags;
    }
}
