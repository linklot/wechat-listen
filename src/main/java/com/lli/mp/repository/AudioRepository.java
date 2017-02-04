package com.lli.mp.repository;

import com.lli.mp.entity.Audio;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AudioRepository extends MongoRepository<Audio, String> {
}
