package com.tim20.rivera.service;

import com.tim20.rivera.model.Tag;
import com.tim20.rivera.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TagService {

    @Autowired
    private TagRepository tagRepository;

    public Tag getOrAddTagByName(String name) {
        Optional<Tag> opt = tagRepository.findByName(name);
        return opt.isEmpty() ? addTag(name) : opt.get();
    }

    public Tag addTag(String name) {
        Tag tag = new Tag();
        tag.setName(name);
        tagRepository.save(tag);
        return tag;
    }

    public List<Tag> getTagsByNames(List<String> names) {
        List<Tag> tags = new ArrayList<Tag>();
        for (String name : names) {
            if (tagRepository.findByName(name).isPresent()) {
                tags.add(tagRepository.findByName(name).get());
            }
        }
        return tags;
    }

    public void addTagsIfNotPresent(List<String> names) {
        for (String name : names) {
            if (tagRepository.findByName(name).isEmpty()) {
                Tag tag = new Tag();
                tag.setName(name);
                tagRepository.save(tag);
            }
        }
    }
}
