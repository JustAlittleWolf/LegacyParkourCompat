import re, pathlib, hashlib, subprocess, json
root=pathlib.Path(r'D:\Javastuff\LegacyParkourCompat\decompiled_minecraft')
vers=['1.18.2','1.19','1.19.1','1.19.2','1.19.3','1.19.4']
paths=['net/minecraft/client/Minecraft.java','net/minecraft/client/multiplayer/ClientLevel.java','net/minecraft/client/player/LocalPlayer.java','net/minecraft/world/entity/player/Player.java','net/minecraft/world/entity/LivingEntity.java','net/minecraft/world/entity/Entity.java','net/minecraft/client/player/Input.java','net/minecraft/client/player/KeyboardInput.java']
# strip comments and string contents while retaining offsets/newlines
pat=re.compile(r'//[^\n]*|/\*.*?\*/|"(?:\\.|[^"\\])*"|\'(?:\\.|[^\'\\])*\'',re.S)
def mask(s):
    return pat.sub(lambda m: ''.join('\n' if c=='\n' else ' ' for c in m.group()),s)
def methods(path,ver):
    s=(root/ver/'mojmap'/path).read_text(encoding='utf-8'); m=mask(s)
    # find opening brace for class, then top-level declarations; identify member declarations by parentheses before first ; or { at depth 1
    depth=0; i=0; out={}
    while i<len(m):
        if m[i]=='{': depth+=1; i+=1; continue
        if m[i]=='}': depth-=1; i+=1; continue
        if depth==1:
            # from previous boundary to next { or ;
            j=i
            while j<len(m) and m[j] not in '{};': j+=1
            if j<len(m) and m[j]=='{' and '(' in m[i:j]:
                head=s[i:j].strip()
                if re.search(r'\b(?:public|protected|private)\b',head) and not re.search(r'\b(?:if|for|while|switch|catch|synchronized|new)\s*\(',head):
                    nm=re.search(r'([A-Za-z_$][\w$]*)\s*\([^()]*\)\s*(?:throws\s+[^\{]+)?$',head,re.S)
                    # Find matching body brace from j.
                    d=1;k=j+1
                    while k<len(m) and d:
                        if m[k]=='{': d+=1
                        elif m[k]=='}': d-=1
                        k+=1
                    if nm:
                        key=re.sub(r'\s+',' ',head[head.rfind('\n')+1:] or head)
                        # Normalize method identity as name + parameter declaration text
                        sig=nm.group(0).split('throws')[0].strip()
                        ident=re.sub(r'\s+',' ',sig)
                        body=m[j:k]
                        out.setdefault(ident,[]).append((s.count('\n',0,i)+1,hashlib.sha256(body.encode()).hexdigest()))
                    i=k; continue
                i=j+1; continue
            if j<len(m) and m[j]==';': i=j+1; continue
        i+=1
    return {k:v[0] for k,v in out.items()}
for a,b in zip(vers,vers[1:]):
 print(f'## {a} -> {b}')
 for p in paths:
  old=methods(p,a); new=methods(p,b)
  delta=[k for k in set(old)|set(new) if old.get(k)!=new.get(k)]
  if delta:
   print(p)
   for k in sorted(delta): print(('  CHG ' if k in old and k in new else '  ADD ' if k in new else '  REM ')+k+' | '+str(old.get(k))+' -> '+str(new.get(k)))
