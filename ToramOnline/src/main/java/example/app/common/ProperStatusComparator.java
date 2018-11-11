package example.app.common;

import java.util.Comparator;

import example.app.dto.ProperStatusDto;

public class ProperStatusComparator implements Comparator<ProperStatusDto> {

	@Override
	public int compare(ProperStatusDto arg0, ProperStatusDto arg1) {

		int SenzaiTanka0 = arg0.getiProperSenzaiTanka();
		int SenzaiTanka1 = arg1.getiProperSenzaiTanka();
		int MokuhyoLevel0 = arg0.getiProperMokuhyoLevel();
		int MokuhyoLevel1 = arg1.getiProperMokuhyoLevel();
		
		if( SenzaiTanka0 < SenzaiTanka1) {
			return -1;
		}else if (SenzaiTanka0 == SenzaiTanka1) {
			if(MokuhyoLevel0 < MokuhyoLevel1) {
				return -1;
			}else if(MokuhyoLevel0 == MokuhyoLevel1) {
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
