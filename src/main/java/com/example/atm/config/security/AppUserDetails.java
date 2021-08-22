package com.example.atm.config.security;

import com.example.atm.model.entity.AppUserEntity;
import com.example.atm.model.entity.ClientCardEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class AppUserDetails implements UserDetails {

    private AppUserEntity appUserEntity;

    public AppUserDetails(AppUserEntity appUserEntity) {
        this.appUserEntity = appUserEntity;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("USER"));
    }

    @Override
    public String getPassword() {
        final List<ClientCardEntity> cardEntities = appUserEntity.getClientCards();
        if (Objects.nonNull(cardEntities) && cardEntities.size() > 0) {
            return cardEntities.get(0).getPassword();
        }
        throw new IllegalArgumentException("Such card code does not exists");
    }

    @Override
    public String getUsername() {
        final List<ClientCardEntity> cardEntities = appUserEntity.getClientCards();
        if (Objects.nonNull(cardEntities) && cardEntities.size() > 0) {
            return cardEntities.get(0).getCardCode();
        }
        throw new IllegalArgumentException("Such card code does not exists");
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
