import hashlib,pathlib
for p in [
'.native-source/20261031/1182-1194-finish50/tick_core_residual/closure.md',
'.native-source/20261031/1182-1194-finish50/tick_core_residual/member-dispositions.tsv',
'.native-source/20261031/1182-1194-finish50/protocol_nonclientbound/local-player-control-gate.md']:
 b=pathlib.Path(p).read_bytes(); print(hashlib.sha256(b).hexdigest(),p)
