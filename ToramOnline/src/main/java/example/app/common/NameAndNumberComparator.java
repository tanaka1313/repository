package example.app.common;

import java.util.Comparator;

import example.app.dto.NameAndNumber;

public class NameAndNumberComparator implements Comparator<NameAndNumber> {
	
	int penalty = 0;
	
	public NameAndNumberComparator(int penalty) {
		this.penalty = penalty;
	}

	@Override
	public int compare(NameAndNumber arg0, NameAndNumber arg1) {
		
		double tanka0 = arg0.getiTanka() * ( (double) (100 + penalty) / (double) 100 );
		double tanka1 = arg1.getiTanka() * ( (double) (100 + penalty) / (double) 100 );
		
		double setuyakuTanka0 = tanka0 - Math.floor(0.00000000000009 + tanka0);
		double setuyakuTanka1 = tanka1 - Math.floor(0.00000000000009 + tanka1);
		
		if( setuyakuTanka0 < setuyakuTanka1) {
			return -1;
		}else if (setuyakuTanka0 == setuyakuTanka1) {
			if( tanka0 < tanka1 ) {
				return -1;
			}else if(tanka0 == tanka1) {
				return 0;
			}else {
				return 1;
			}
		}else {
			return 1;
		}
	}

}
