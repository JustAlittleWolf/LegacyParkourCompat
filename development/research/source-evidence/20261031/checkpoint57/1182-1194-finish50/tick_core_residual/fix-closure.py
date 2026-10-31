from pathlib import Path
p=Path('.native-source/20261031/1182-1194-finish50/tick_core_residual/closure.md')
s=p.read_text(encoding='utf-8-sig')
s=s.replace('in `independent_coverage_audit/edge-backoff-review.md; this is a candidate', 'in `independent_coverage_audit/edge-backoff-review.md`; this is a candidate')
p.write_text(s,encoding='utf-8')
