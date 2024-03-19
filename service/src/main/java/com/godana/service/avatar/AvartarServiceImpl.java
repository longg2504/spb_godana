package com.godana.service.avatar;

import com.godana.domain.entity.Avatar;
import com.godana.repository.avatar.AvatarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class AvartarServiceImpl implements IAvatarService{
    @Autowired
    private AvatarRepository avatarRepository;
    @Override
    public List<Avatar> findAll() {
        return avatarRepository.findAll();
    }

    @Override
    public Optional<Avatar> findById(String id) {
        return avatarRepository.findById(id);
    }

    @Override
    public Avatar save(Avatar avatar) {
        return avatarRepository.save(avatar);
    }

    @Override
    public void delete(Avatar avatar) {
        avatarRepository.delete(avatar);
    }

    @Override

    public void deleteById(String id) {
    }
}
