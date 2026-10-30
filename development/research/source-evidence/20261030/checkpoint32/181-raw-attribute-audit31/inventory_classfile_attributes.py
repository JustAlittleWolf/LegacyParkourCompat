import argparse, collections, hashlib, json, pathlib, struct, zipfile
KNOWN = set('''ConstantValue Code StackMapTable Exceptions InnerClasses EnclosingMethod Synthetic Signature SourceFile SourceDebugExtension LineNumberTable LocalVariableTable LocalVariableTypeTable Deprecated RuntimeVisibleAnnotations RuntimeInvisibleAnnotations RuntimeVisibleParameterAnnotations RuntimeInvisibleParameterAnnotations RuntimeVisibleTypeAnnotations RuntimeInvisibleTypeAnnotations AnnotationDefault BootstrapMethods MethodParameters Module ModulePackages ModuleMainClass NestHost NestMembers Record PermittedSubclasses'''.split())
class Reader:
    def __init__(self,b): self.b=b; self.p=0
    def u1(self): v=self.b[self.p]; self.p+=1; return v
    def u2(self): v=struct.unpack_from('>H',self.b,self.p)[0]; self.p+=2; return v
    def u4(self): v=struct.unpack_from('>I',self.b,self.p)[0]; self.p+=4; return v
    def take(self,n): v=self.b[self.p:self.p+n]; self.p+=n; return v
def parse_class(data,entry,counts,occurrences):
    r=Reader(data)
    if r.u4()!=0xCAFEBABE: raise ValueError(f'{entry}: bad magic')
    r.u2(); r.u2(); n=r.u2(); cp=[None]*n; i=1
    while i<n:
        tag=r.u1()
        if tag==1: ln=r.u2(); cp[i]=('utf8',r.take(ln).decode('utf-8','replace'))
        elif tag in (3,4): r.take(4)
        elif tag in (5,6): r.take(8); i+=1
        elif tag in (7,8,16,19,20): cp[i]=(tag,r.u2())
        elif tag in (9,10,11,12,17,18): r.take(4)
        elif tag==15: r.take(3)
        else: raise ValueError(f'{entry}: unsupported CP tag {tag} at {i}')
        i+=1
    def utf(index):
        x=cp[index]
        if not x or x[0]!='utf8': raise ValueError(f'{entry}: attr name CP index {index} is not UTF8')
        return x[1]
    def attrs(reader,count,context):
        for _ in range(count):
            name=utf(reader.u2()); length=reader.u4(); payload=reader.take(length)
            counts[(context,name)]+=1; occurrences[name].append((entry,context,length))
            if context=='method' and name=='Code':
                cr=Reader(payload); cr.u2(); cr.u2(); code_len=cr.u4(); cr.take(code_len); ex=cr.u2(); cr.take(ex*8); na=cr.u2(); attrs(cr,na,'Code')
                if cr.p!=len(payload): raise ValueError(f'{entry}: Code trailing bytes {len(payload)-cr.p}')
    r.take(6); r.take(r.u2()*2)
    for kind in ('field','method'):
        for _ in range(r.u2()): r.take(6); attrs(r,r.u2(),kind)
    attrs(r,r.u2(),'class')
    if r.p!=len(data): raise ValueError(f'{entry}: trailing bytes {len(data)-r.p}')
def main():
    ap=argparse.ArgumentParser(); ap.add_argument('jar'); ap.add_argument('out'); a=ap.parse_args(); path=pathlib.Path(a.jar)
    sha=hashlib.sha256(path.read_bytes()).hexdigest(); counts=collections.Counter(); occurrences=collections.defaultdict(list); classes=0
    with zipfile.ZipFile(path) as z:
        for info in z.infolist():
            if info.filename.endswith('.class'): classes+=1; parse_class(z.read(info),info.filename,counts,occurrences)
    attrs=sorted({name for _,name in counts})
    report={'jar':str(path),'sha256':sha,'class_count':classes,'attribute_occurrence_total':sum(counts.values()),'attribute_names':attrs,'unknown_names':sorted(set(attrs)-KNOWN),'contexts':{name:{ctx:counts[(ctx,name)] for ctx in ('class','field','method','Code') if counts[(ctx,name)]} for name in attrs},'occurrences':{name:[{'entry':e,'context':c,'length':n} for e,c,n in rows] for name,rows in occurrences.items() if name not in KNOWN}}
    pathlib.Path(a.out).write_text(json.dumps(report,indent=2),encoding='utf-8'); print(json.dumps({k:report[k] for k in ('sha256','class_count','attribute_occurrence_total','attribute_names','unknown_names','contexts')},indent=2))
if __name__=='__main__': main()


