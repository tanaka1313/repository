package example.app.common;

import java.util.Comparator;

import example.app.dto.ProperHuyoDto;

public class ProperHuyoComparator implements Comparator<ProperHuyoDto> {

	@Override
	public int compare(ProperHuyoDto arg0, ProperHuyoDto arg1) {

		int sortNum0 = arg0.getiProperSortNum();
		int sortNum1 = arg1.getiProperSortNum();
		String properName0 = arg0.getvProperName();
		String properName1 = arg1.getvProperName();
		int properLv0 = arg0.getiProperNowLevel();
		int properLv1 = arg1.getiProperNowLevel();
		
		if( sortNum0 < sortNum1) {
			return -1;
		}else if (sortNum0 == sortNum1) {
			if(properName0 == null) properName0 = "";
			int a = properName0.compareTo(properName1);
			if(a < 0) {
				return -1;
			}else if(a == 0) {
				if(properLv0 < properLv1) {
					return -1;
				}else if(properLv0 == properLv1) {
					return 0;
					
				}else {
					return 1;
				}
			}
			else {
				return 1;
			}
		}else {
			return 1;
		}
	}

}
