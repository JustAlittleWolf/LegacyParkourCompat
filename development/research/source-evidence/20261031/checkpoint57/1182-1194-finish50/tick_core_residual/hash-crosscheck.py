import csv,pathlib
base=pathlib.Path('.native-source/20261031/1182-1194-finish50/tick_core_residual')
h={}
for ln in (base/'source-hashes.tsv').read_text(encoding='utf-8-sig').splitlines():
 v,p,d=ln.split('\t');h[(v,p)]=d
paths=set(p for v,p in h)
rows=list(csv.DictReader(pathlib.Path('.native-source/20261030/1182-1194-inventory44/changed-paths.tsv').open(encoding='utf-8-sig'),delimiter='\t'))
ver={'1.18.2->1.19':('1.18.2','1.19'),'1.19->1.19.1':('1.19','1.19.1'),'1.19.1->1.19.2':('1.19.1','1.19.2'),'1.19.2->1.19.3':('1.19.2','1.19.3'),'1.19.3->1.19.4':('1.19.3','1.19.4')}
errors=[]
for e,(o,n) in ver.items():
 for p in paths:
  rs=[r for r in rows if r['edge']==e and r['path']==p]
  if rs:
   r=rs[0]
   if r.get('old_sha256') and r['old_sha256'].lower()!=h[(o,p)]: errors.append((e,p,'old'))
   if r.get('new_sha256') and r['new_sha256'].lower()!=h[(n,p)]: errors.append((e,p,'new'))
  elif h[(o,p)]!=h[(n,p)]: errors.append((e,p,'unrostered change'))
print('core_path_hash_crosscheck_errors='+str(len(errors)))
for e in errors: print(e)
