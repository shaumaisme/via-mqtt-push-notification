package com.jakartawebs.learn.web;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.validator.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jakartawebs.learn.entity.Notification;
import com.jakartawebs.learn.entity.NotificationRepository;

/**
 * Controller for handling notification submissions and listing of notifications.
 * 
 * @author zakyalvan
 */
@RestController
@RequestMapping("/notifications")
public class NotificationController {
	private static final Logger LOGGER = LoggerFactory.getLogger(NotificationController.class);
	
	@Autowired
	private NotificationRepository notificationRepository;
	
	@RequestMapping(method=RequestMethod.POST)
	public HttpEntity<Notification> submitNotification(@Validated @RequestBody NotificationForm form, BindingResult bindingResult) {
		LOGGER.debug("Handle new notifications submissions");
		if(bindingResult.hasErrors()) {
			return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
		}
		
		/**
		 * FIXME
		 * 
		 * Use dozer here for binding new object! See http://dozer.sourceforge.net/
		 */
		Notification notification = new Notification();
		notification.setSubject(form.getSubject());
		notification.setContent(form.getContent());
		if(form.targets.size() > 0) {
			notification.setTargeted(true);
			notification.setTargets(form.getTargets());
		}
		else {
			notification.setTargeted(false);
		}
		notification.setPublished(false);
		Notification persistedNotification = notificationRepository.save(notification);
		return new ResponseEntity<Notification>(persistedNotification, HttpStatus.CREATED);
	}
	
	/**
	 * Notification form backing object.
	 * 
	 * @author zakyalvan
	 */
	@SuppressWarnings("serial")
	public static class NotificationForm implements Serializable {
		/**
		 * Subject of notification.
		 */
		@NotBlank
		private String subject;
		
		/**
		 * Content of notification.
		 */
		@NotBlank
		private String content;
		
		/**
		 * Username as targets of notification.
		 */
		private Set<String> targets = new HashSet<>();
		
		/**
		 * Scheduled publish date.
		 */
		private Date publishDate;

		public String getSubject() {
			return subject;
		}
		public void setSubject(String subject) {
			this.subject = subject;
		}

		public String getContent() {
			return content;
		}
		public void setContent(String content) {
			this.content = content;
		}

		public Set<String> getTargets() {
			return new HashSet<>(targets);
		}
		public void setTargets(Set<String> targets) {
			this.targets.addAll(targets);
		}

		public Date getPublishDate() {
			return publishDate;
		}
		public void setPublishDate(Date publishDate) {
			this.publishDate = publishDate;
		}
	}
}
