package com.tim20.rivera.service;

import com.tim20.rivera.model.Tag;
import com.tim20.rivera.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TagService {
    @Autowired
    private TagRepository tagRepository;

    public List<Tag> getTagsByNames(List<String> names){
        List<Tag> tags = new ArrayList<Tag>();
        for(String name : names){
            if(tagRepository.findByName(name).isPresent()){
                tags.add(tagRepository.findByName(name).get());
            }
        }
        return tags;
    }

    public void addTagsIfNotPresent(List<String> names) {
        for(String name : names){
            if(tagRepository.findByName(name).isEmpty()){
                Tag tag = new Tag();
                tag.setName(name);
                tagRepository.save(tag);
            }
        }
    }
}
