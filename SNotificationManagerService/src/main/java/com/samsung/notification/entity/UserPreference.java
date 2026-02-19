package com.samsung.notification.entity;

import jakarta.persistence.*;
import com.samsung.notification.enums.PopupStyle;

@Entity
@Table(name="user_preferences")
public class UserPreference {

    @Id
    private String userId;

    @Enumerated(EnumType.STRING)
    private PopupStyle popupStyle;

    private boolean enabled;

    @Version
    private Long version;

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public PopupStyle getPopupStyle() { return popupStyle; }
    public void setPopupStyle(PopupStyle popupStyle) { this.popupStyle = popupStyle; }

    public boolean isEnabled() { return enabled; }
    public void setEnabled(boolean enabled) { this.enabled = enabled; }
}
