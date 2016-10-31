package edu.tp.paw.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table( name = "sales" )
public class Purchase {

	@GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "sales_sale_id_seq" )
	@SequenceGenerator( sequenceName = "sales_sale_id_seq", name = "sales_sale_id_seq", allocationSize = 1 )
	@Column( name =  "sale_id")
	@Id
	private Long id;
	
	@ManyToOne( fetch = FetchType.EAGER )
	@JoinColumn( name =  "user_id", foreignKey = @ForeignKey( name = "user_fk" ))
	private User buyer;
	
	@ManyToOne( fetch = FetchType.EAGER )
	@JoinColumn( name =  "item_id", foreignKey = @ForeignKey( name = "item_fk" ))
	private StoreItem item;
	
	private boolean approved;

	/* protected */ Purchase() {
		// hibernate, duh!
	}
	
	public Long getId() {
		return id;
	}

	public User getBuyer() {
		return buyer;
	}

	public StoreItem getItem() {
		return item;
	}

	public boolean isApproved() {
		return approved;
	}
	
	public boolean getApproved() {
		return approved;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Purchase)) {
			return false;
		}
		Purchase other = (Purchase) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}
	
}
