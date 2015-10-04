package com.jakartawebs.learn.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OrderColumn;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.hibernate.validator.constraints.NotBlank;

/**
 * 
 * @author zakyalvan
 */
@Entity
@Table(name="violet_notification")
@SuppressWarnings("serial")
public class Notification implements Serializable {
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="violet_notification_seq")
	@SequenceGenerator(name="violet_notification_seq", sequenceName="violet_notification_seq")
	private Long id;
	
	@NotBlank
	@Column(name="subject")
	private String subject;
	
	@NotBlank
	@Column(name="content")
	private String content;
	
	/**
	 * Whether this notification is targeted to specific users or not.
	 * If false, notification will be pushed to all users.
	 */
	@Column(name="is_targeted")
	private boolean targeted;
	
	@ElementCollection(fetch=FetchType.EAGER)
	@CollectionTable(name="violet_notification_target", joinColumns=@JoinColumn(name="notification_id", referencedColumnName="id"))
	@Column(name="target_username")
	@OrderColumn(name="sequence", nullable=false)
	private Set<String> targets = new HashSet<>();
	
	@Column(name="submitted_timestamp")
	private Date submittedTimestamp;
	
	@Column(name="is_published")
	private boolean published;
	
	@Column(name="published_timestamp")
	@Temporal(TemporalType.TIMESTAMP)
	private Date publishedTimestamp;
	
	@Version
	@Column(name="record_version")
	private Integer version;

	public Long getId() {
		return id;
	}
	
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

	public boolean isTargeted() {
		return targeted;
	}
	public void setTargeted(boolean targeted) {
		this.targeted = targeted;
	}

	public Set<String> getTargets() {
		return targets;
	}
	public void setTargets(Set<String> targets) {
		this.targets = targets;
	}

	public Date getSubmittedTimestamp() {
		return submittedTimestamp;
	}
	public void setSubmittedTimestamp(Date submittedTimestamp) {
		this.submittedTimestamp = submittedTimestamp;
	}

	public boolean isPublished() {
		return published;
	}
	public void setPublished(boolean published) {
		this.published = published;
	}

	public Date getPublishedTimestamp() {
		return publishedTimestamp;
	}
	public void setPublishedTimestamp(Date publishedTimestamp) {
		this.publishedTimestamp = publishedTimestamp;
	}

	public Integer getVersion() {
		return version;
	}

	@Override
	public String toString() {
		return "Notification [id=" + id + ", subject=" + subject + ", content=" + content + ", targeted=" + targeted
				+ ", targets=" + targets + ", submittedTimestamp=" + submittedTimestamp + ", published=" + published
				+ ", publishedTimestamp=" + publishedTimestamp + ", version=" + version + "]";
	}
}
