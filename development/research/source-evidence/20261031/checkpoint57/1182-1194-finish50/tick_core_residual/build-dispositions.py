import csv,pathlib
base=pathlib.Path('.native-source/20261031/1182-1194-finish50/tick_core_residual')
hashes={}
for line in (base/'source-hashes.tsv').read_text(encoding='utf-8-sig').splitlines():
 v,p,h=line.split('\t');hashes[(v,p)]=h
versions={'1.18.2->1.19':('1.18.2','1.19'),'1.19->1.19.1':('1.19','1.19.1'),'1.19.1->1.19.2':('1.19.1','1.19.2'),'1.19.2->1.19.3':('1.19.2','1.19.3'),'1.19.3->1.19.4':('1.19.3','1.19.4')}
rows=list(csv.DictReader((base/'member-dispositions-raw.tsv').open(encoding='utf-8-sig'),delimiter='\t'))
with (base/'member-dispositions.tsv').open('w',encoding='utf-8',newline='') as f:
 w=csv.writer(f,delimiter='\t',lineterminator='\n');w.writerow(['edge','path','member','old_anchor','new_anchor','old_source_sha256','new_source_sha256','disposition','evidence'])
 for r in rows:
  ov,nv=versions[r['edge']]; w.writerow([r['edge'],r['path'],r['member'],r['old_anchor'],r['new_anchor'],hashes[(ov,r['path'])],hashes[(nv,r['path'])],r['disposition'],r['reference']])
