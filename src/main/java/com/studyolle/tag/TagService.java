package com.studyolle.tag;

import com.studyolle.domain.Account;
import com.studyolle.domain.AccountTag;
import com.studyolle.domain.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@Service
public class TagService {

    private final TagRepository tagRepository;
    private final AccountTagRepository accountTagRepository;

    public void addTag(Account account, String tagTitle) {
        Tag tag = tagRepository.findByTitle(tagTitle)
                .orElseGet(() -> tagRepository.save(Tag.builder().title(tagTitle).build()));
        accountTagRepository.save(AccountTag.builder()
                .account(account).tag(tag).build());
    }

    public List<String> getAllTags(){
        return tagRepository.findAllByOrderByTitle().stream()
                .map(Tag::getTitle).collect(toList());
    }

    public List<String> getTags(Account account){
        return accountTagRepository.findByAccount(account).stream()
                .map(AccountTag::getTitle).collect(toList());
    }

    public boolean removeTag(Account account, String tagTitle){
        boolean hasRemoved = false;

        Optional<Tag> byTitle = tagRepository.findByTitle(tagTitle);
        if(byTitle.isPresent()) {
            AccountTag accountTag = accountTagRepository.findAccountTag(account, byTitle.get());
            accountTagRepository.delete(accountTag);
            hasRemoved = true;
        }
        return hasRemoved;
    }
}
