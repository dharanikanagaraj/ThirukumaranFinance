package com.java.finance.ThirukumaranFinance.Entity;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "user_loan_details", schema = "public")
public class Loan {

	private static final long serialversionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_LOAN_DETAILS_SEQ") // needs to create sequence
	@SequenceGenerator(name = "USER_LOAN_DETAILS_SEQ", sequenceName = "USER_LOAN_DETAILS_SEQ", allocationSize = 1)
	@Column(name = "loan_id")
	private int loanId;

	@Column(name = "loan_no")
	private String loanNo; // sent from UI which should be next and next in serial order (eg:LNo01)

	@Column(name = "user_no")
	private String userNo; // sent from UI which should be next and next in serial order (eg:US01)

	@Column(name = "name")
	private String name;

	@Column(name = "address")
	private String address;

	@Column(name = "phone_no")
	private String phoneNo;

	@Column(name = "order_no")
	private String orderNo;

	@Column(name = "current_loan_date")
	private LocalDate currentLoanDate;

	@Column(name = "loan_amount")
	private int loanAmount;

	@Column(name = "seetu_amount")
	private int seetuAmount;

	@Column(name = "commission_amount")
	private int commissionAmount;

	@Column(name = "interest")
	private int interest;

	@Column(name = "appx_loan_closed_date") // calculate and save it in DB from backend
	private LocalDate appxLoanClosedDate;

	@Column(name = "loan_closed_date") // have it as empty while creating
	private LocalDate loanClosedDate;

	@Column(name = "excess_amount") // have it as 0 while creating
	private int excessAmount;

	@Column(name = "line_id")
	private String lineId;

	@Column(name = "balance") // while creating loan balance will be loan amount which further reduces on
								// daily basis
	private int balance;

	@Column(name = "is_loan_active") // while creating it will be true by default which we update during loan close
	private boolean isLoanActive;

	@Column(name = "daily_update") // By Default it will be false when daily amount is updated for that particular
									// loan it update as true during date close all active loan will be updated by
									// false
	private boolean dailyUpdate;
	
//	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY) 
//    @JoinColumn(name ="loan_id")
//    private List<Dailycollection> dailyCollectionList;

}
