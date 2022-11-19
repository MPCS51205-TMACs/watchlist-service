package com.example.watchlistservice.security

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.User
import java.util.*

data class AuthenticatedUser(val userId: UUID,val email:String, val grantedAuthorities: Collection<GrantedAuthority>) :
    User(userId.toString(), "", grantedAuthorities)