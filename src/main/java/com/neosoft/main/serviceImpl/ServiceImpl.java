package com.neosoft.main.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.neosoft.main.model.Student;
import com.neosoft.main.repository.StudentRepo;
import com.neosoft.main.serviceInterf.ServiceInterf;

@Service
public class ServiceImpl implements UserDetailsService, ServiceInterf {

	@Autowired
	StudentRepo studentRepo;
	@Override
	public List<Student> getAllStudent() {
       
		List<Student> list=studentRepo.findAll();

		return list;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	
		return new User("admin", "admin", new ArrayList<>());
	}

}
