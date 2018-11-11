package example.app.common;

import java.util.Comparator;

import example.app.dto.ProperStatusDto;

public class ProperStatusComparator2 implements Comparator<ProperStatusDto> {

	@Override
	public int compare(ProperStatusDto arg0, ProperStatusDto arg1) {

		int SenzaiTanka0 = arg0.getiProperSenzaiTanka();
		int SenzaiTanka1 = arg1.getiProperSenzaiTanka();
		int MokuhyoLevel0 = arg0.getiProperMokuhyoLevel();
		int MokuhyoLevel1 = arg1.getiProperMokuhyoLevel();
		
		int needSenzai0 = SenzaiTanka0 * MokuhyoLevel0;
		int needSenzai1 = SenzaiTanka1 * MokuhyoLevel1;
		
		if( needSenzai0 < needSenzai1) {
			return -1;
		}else if (needSenzai0 == needSenzai1) {
				return 0;
		}else {
			return 1;
		}
	}

}
