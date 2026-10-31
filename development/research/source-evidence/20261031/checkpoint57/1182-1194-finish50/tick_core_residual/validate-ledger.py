import csv,pathlib
p=pathlib.Path('.native-source/20261031/1182-1194-finish50/tick_core_residual/member-dispositions.tsv')
rows=list(csv.DictReader(p.open(encoding='utf-8-sig'),delimiter='\t'))
assert rows and all(len(r['old_source_sha256'])==64 and len(r['new_source_sha256'])==64 for r in rows)
assert all(r['old_anchor'] and r['new_anchor'] for r in rows)
print(f'rows={len(rows)} malformed_hash_or_anchor=0')
