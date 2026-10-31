from pathlib import Path
import re
src=Path('.native-source/20261031/1182-1194-finish50/tick_core_residual/method-diff-index.txt').read_text(encoding='utf-8-sig')
out=[]
for line in src.splitlines():
    if line.startswith('## ') or line.startswith('net/minecraft/'):
        out.append(line)
    elif re.match(r'  (CHG|ADD|REM) ',line):
        hashes=re.findall(r"'([0-9a-f]{64})'",line)
        if 'ADD ' in line or 'REM ' in line or (len(hashes)>1 and hashes[0]!=hashes[1]): out.append(line)
Path('.native-source/20261031/1182-1194-finish50/tick_core_residual/actual-method-changes.txt').write_text('\n'.join(out),encoding='utf-8')
