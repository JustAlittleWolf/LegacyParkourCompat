import hashlib,zipfile,struct,pathlib,json
p=pathlib.Path('D:/Javastuff/LegacyParkourCompat/.minecraft-decompile-cache')
z=zipfile.ZipFile(p/'1.8/client-ornithe-feather.jar')
d=hashlib.sha256(b'LPC-JAR-CONTENT-v1\0')
ns=z.namelist()
assert len(ns)==len(set(ns))
for n in sorted(ns):
    data=z.read(n); name=n.encode()
    d.update(struct.pack('>I',len(name))); d.update(name)
    d.update(struct.pack('>Q',len(data))); d.update(data)
result={'mappedContent':d.hexdigest()}
for f in [p/'1.8/client.jar',p/'yarn/feather-gen2-1.8+build.2-mergedv2.jar',p/'yarn/feather-gen2-1.8+build.2.tiny']:
    result[str(f)]=hashlib.sha256(f.read_bytes()).hexdigest()
print(json.dumps(result,indent=2))
