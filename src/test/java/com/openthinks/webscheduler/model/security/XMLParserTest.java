package com.openthinks.webscheduler.model.security;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.junit.Test;

import com.openthinks.webscheduler.help.StaticUtils;

public class XMLParserTest {

	@Test
	public void testMarshaller() throws JAXBException {
		Roles roles = new Roles();
		Role role = new Role();
		role.setId("1");
		role.setName("test");
		role.setDesc("test desc");
		roles.add(role);
		role = new Role();
		role.setId("2");
		role.setName("test2");
		role.setDesc("test2 desc");
		roles.add(role);

		Users users = new Users();
		User user = new User();
		user.setId("u1");
		user.setEmail("test@email.com");
		user.setName("uname");
		user.setPass("upass");
		user.setRoles(roles.getRoles());

		RememberMeCookie cookie = new RememberMeCookie();
		cookie.setToken("abcd");
		cookie.setExpireTime(StaticUtils.formatNow());
		user.setCookie(cookie);
		users.add(user);

		WebSecurity ws = new WebSecurity();
		ws.setRoles(roles);
		ws.setUsers(users);
		JAXBContext jaxbContext = JAXBContext.newInstance(WebSecurity.class);
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		jaxbMarshaller.marshal(ws, System.out);
	}

	@Test
	public void testUnMarshaller() throws JAXBException {
		JAXBContext jaxbContext = JAXBContext.newInstance(WebSecurity.class);
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
		//unmarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		WebSecurity ws = (WebSecurity) unmarshaller
				.unmarshal(new File("R:\\MyGit\\webscheduler\\src\\main\\resources\\conf\\security.xml"));
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		jaxbMarshaller.marshal(ws, System.out);
	}
}
