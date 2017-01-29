package com.lli.mp.repository;

import com.lli.mp.entity.Audio;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AudioRepository extends MongoRepository<Audio, String> {
}
