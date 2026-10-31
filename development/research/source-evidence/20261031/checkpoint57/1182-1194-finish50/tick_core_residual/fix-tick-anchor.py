from pathlib import Path
p=Path('.native-source/20261031/1182-1194-finish50/tick_core_residual/member-dispositions-raw.tsv')
s=p.read_text(encoding='utf-8-sig').replace('tick graph\t1684\t1723', 'tick graph\t1654\t1655')
p.write_text(s,encoding='utf-8')
