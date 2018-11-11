package example.app.common;

import java.util.Comparator;

import example.app.dto.ProperStatusDto;

public class ProperStatusComparator3 implements Comparator<ProperStatusDto> {
	
	// ペナルティ値
	int penalty = 0;
	
	// コンストラクタ
	public ProperStatusComparator3(int penalty) {
		this.penalty = penalty;
	}

	@Override
	public int compare(ProperStatusDto arg0, ProperStatusDto arg1) {

		int SenzaiTanka0 = arg0.getiProperSenzaiTanka();
		int SenzaiTanka1 = arg1.getiProperSenzaiTanka();
		
		double syohiTanka0 = SenzaiTanka0 * ((double)(100 + penalty) / (double)100);
		double sabun0 = syohiTanka0 - Math.floor(0.00000000000009 + syohiTanka0);
		double syohiTanka1 = SenzaiTanka1 * ((double)(100 + penalty) / (double)100);
		double sabun1 = syohiTanka1 - Math.floor(0.00000000000009 + syohiTanka1);
		
		if( sabun0 < sabun1) {
			return -1;
		}else if (SenzaiTanka0 == SenzaiTanka1) {
			if(SenzaiTanka0 < SenzaiTanka1) {
				return -1;
			}else if(sabun0 == sabun1) {
				return 0;
			}
			else {
				return 1;
			}
		}else {
			return 1;
		}
	}

}
