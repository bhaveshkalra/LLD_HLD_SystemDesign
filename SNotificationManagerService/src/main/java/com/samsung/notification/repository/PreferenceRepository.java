package com.samsung.notification.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.samsung.notification.entity.UserPreference;

@Repository
public interface PreferenceRepository extends JpaRepository<UserPreference, String> {
}
