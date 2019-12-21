package feed.builder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.teradata.model.ColumnDetails;
import com.thinkbiganalytics.discovery.schema.Field;
import com.thinkbiganalytics.policy.rest.model.FieldPolicy;
import com.thinkbiganalytics.policy.rest.model.FieldStandardizationRule;
import com.thinkbiganalytics.policy.rest.model.FieldValidationRule;

public class PolicyBuilder extends FeedBuilder {
	   private String fieldName;
	    private int seq;
	    private List<FieldStandardizationRule> standardisations = new ArrayList<>();
	    private List<FieldValidationRule> validations = new ArrayList<>();
	    private boolean profile;
	    private boolean index;

	    public PolicyBuilder() {
	      
	    }
	    public PolicyBuilder(String fieldName) {
	        this.fieldName = fieldName;
	    }

	    public PolicyBuilder withStandardisation(FieldStandardizationRule... rules) {
	        for (FieldStandardizationRule rule : rules) {
	            rule.setSequence(seq++);
	            standardisations.add(rule);
	        }
	        return this;
	    }

	    public PolicyBuilder withValidation(FieldValidationRule... rules) {
	        Arrays.stream(rules).forEach(rule -> {
	            rule.setSequence(seq++);
	            validations.add(rule);
	        });
	        return this;
	    }

	    public PolicyBuilder withProfile() {
	        profile = true;
	        return this;
	    }

	    public PolicyBuilder withIndex() {
	        index = true;
	        return this;
	    }

	    public FieldPolicy toPolicy() {
	        FieldPolicy policy = new FieldPolicy();
	        policy.setFieldName(fieldName);
	        policy.setFeedFieldName(fieldName);
	        policy.setProfile(profile);
	        policy.setIndex(index);
	        policy.setStandardization(standardisations);
	        policy.setValidation(validations);
	        return policy;
	    }
	
	    protected PolicyBuilder newPolicyBuilder(String fieldName) {
	        return new PolicyBuilder(fieldName);
	    }
	    
	public List<FieldPolicy> populateFieldPolicy(List<Field> columnDetails) {
		List<FieldPolicy> filedPolicies=new ArrayList<>();
		columnDetails.forEach(columnDetail->{
			
//		PolicyBuilder policyBuilder=new PolicyBuilder();
//		policyBuilder.setProfile(true);
//		policyBuilder.setFeedFieldName(columnDetail.getField());
//		policyBuilder.setFieldName(columnDetail.getField());
//		policyBuilder.setStandardization(newPolicyBuilder(columnDetail).getStandardization());
//		policyBuilder.setValidate(getValidations());

		filedPolicies.add(newPolicyBuilder(columnDetail.getName()).withProfile().withIndex().toPolicy());
		
		});
		return filedPolicies;
	}
	
	
//	
//
//	private FieldPolicy build() {
//
//		return fp;
//	}
//	

}
