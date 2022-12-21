package br.com.pb.barbershop.msuser.domain.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(name = "user")
    private String name;

    @Column(unique = true)
    private String email;

    private String phone;

    private String document;

    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Profile> profile = new ArrayList<>();



    //@Override
    //public Collection<? extends GrantedAuthority> getAuthorities() {
       //return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    //}

    public void addProfile(Profile newProfile){
        this.profile.add(newProfile);
    }

    public void removeProfile(Profile profile){
        this.profile.remove(profile);
    }
    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.profile;
    }

    @Override
    public String getPassword(){
        return this.password;
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
