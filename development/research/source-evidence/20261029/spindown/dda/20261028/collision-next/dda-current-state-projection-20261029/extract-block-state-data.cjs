const fs = require('node:fs');
const path = require('node:path');
const root = 'D:/Javastuff/LegacyParkourCompat/decompiled_minecraft';
const src = path.join(root, '26.2/unobfuscated/net/minecraft/util/datafix/fixes/BlockStateData.java');
const text = fs.readFileSync(src, 'utf8');
function findCallEnd(s, open) {
  let depth=0, inStr=false, esc=false, line=false, block=false;
  for (let i=open;i<s.length;i++) {
    const c=s[i], n=s[i+1];
    if(line){if(c==='\n') line=false; continue;}
    if(block){if(c==='*'&&n==='/'){block=false;i++;} continue;}
    if(inStr){if(esc){esc=false;continue;} if(c==='\\'){esc=true;continue;} if(c==='"') inStr=false; continue;}
    if(c==='/'&&n==='/'){line=true;i++;continue;} if(c==='/'&&n==='*'){block=true;i++;continue;}
    if(c==='"'){inStr=true;continue;} if(c==='(') depth++; else if(c===')'&&--depth===0) return i;
  }
  throw new Error('unclosed call at '+open);
}
function splitArgs(s) {
  let out=[], start=0, depth=0, inStr=false, esc=false;
  for(let i=0;i<s.length;i++){const c=s[i]; if(inStr){if(esc){esc=false;continue;} if(c==='\\'){esc=true;continue;} if(c==='"')inStr=false;continue;} if(c==='"'){inStr=true;continue;} if(c==='('||c==='{'||c==='[')depth++; else if(c===')'||c==='}'||c===']')depth--; else if(c===','&&depth===0){out.push(s.slice(start,i).trim());start=i+1;}}
  out.push(s.slice(start).trim()); return out;
}
function parseCreate(expr){
  const m=expr.match(/^\s*create\s*\(/); if(!m)return null;
  const open=expr.indexOf('('), end=findCallEnd(expr,open), args=splitArgs(expr.slice(open+1,end));
  const name=(args[0]||'').match(/^"([^"\\]*(?:\\.[^"\\]*)*)"$/)?.[1];
  if(!name)return null;
  return {name, propertiesExpr:args[1]||'', properties:parseProps(args[1]||'')};
}
function parseProps(expr){
  if(!expr)return {};
  let e=expr.trim();
  const mapStart=e.indexOf('Map.of(');
  if(mapStart>=0){const open=e.indexOf('(',mapStart),end=findCallEnd(e,open); const vals=[...e.slice(open+1,end).matchAll(/"((?:\\.|[^"\\])*)"/g)].map(x=>x[1]); if(vals.length%2===0){const o={};for(let i=0;i<vals.length;i+=2)o[vals[i]]=vals[i+1];return o;}}
  return {__unresolved_expression__:e};
}
const constantProps=new Map();
const constRe=/private\s+static\s+final\s+Map<String,\s*String>\s+([A-Z0-9_]+)\s*=\s*Map\.of\s*\(/g;
let cm;
while((cm=constRe.exec(text))){const open=text.indexOf('(',cm.index),end=findCallEnd(text,open),vals=[...text.slice(open+1,end).matchAll(/"((?:\\.|[^"\\])*)"/g)].map(x=>x[1]);if(vals.length%2===0){const o={};for(let i=0;i<vals.length;i+=2)o[vals[i]]=vals[i+1];constantProps.set(cm[1],o);}constRe.lastIndex=end+1;}
function resolvedProps(expr){const p=parseProps(expr);if(p.__unresolved_expression__&&constantProps.has(p.__unresolved_expression__))return constantProps.get(p.__unresolved_expression__);return p;}
const entries=[];
const re=/\bregister\s*\(/g; let m;
while((m=re.exec(text))){const open=text.indexOf('(',m.index), end=findCallEnd(text,open); const args=splitArgs(text.slice(open+1,end));
  if(!/^\d+$/.test(args[0]||''))continue;
  const index=Number(args[0]); const target=parseCreate(args[1]||''); if(!target)continue;
  const legacyAliases=args.slice(2).map(expr=>{const parsed=parseCreate(expr);return parsed?{name:parsed.name,properties:resolvedProps(parsed.propertiesExpr),propertiesExpression:parsed.propertiesExpr}:null;}).filter(Boolean);
  const line=text.slice(0,m.index).split('\n').length;
  entries.push({index,line,target,legacyAliases}); re.lastIndex=end+1;
}
const direct=new Map(), defaults=new Map();
for(const e of entries){direct.set(e.index,e);const id=e.index>>4;if(!defaults.has(id))defaults.set(id,e);}
const output=[];
for(let id=0;id<198;id++)for(let meta=0;meta<16;meta++){
  const index=(id<<4)|meta; const e=direct.get(index);const d=defaults.get(id);const chosen=e||d||direct.get(0);
  output.push({oldId:id,metadata:meta,blockDataIndex:index,lookup:e?'explicit-map-index':d?'block-default-fill':'getTag-index-0-fallback',mappingIndex:chosen?.index??0,targetName:chosen?.target.name??'',targetProperties:chosen?resolvedProps(chosen.target.propertiesExpr):{},targetPropertiesExpression:chosen?.target.propertiesExpr??'',legacyAliases:chosen?.legacyAliases??[],sourceLine:chosen?.line??null,sourceEquivalenceStatus:'candidate-only: BlockStateData.getTag numeric lookup is not itself proof that a 1.8.9 old ID/metadata state equals this later flattened-table index'});
}
fs.mkdirSync(path.resolve('.native-source/20261028/collision-next/dda-current-state-projection-20261029'),{recursive:true});
const out='.native-source/20261028/collision-next/dda-current-state-projection-20261029/block-state-data-legacy-index-projection.jsonl';
fs.writeFileSync(out,output.map(x=>JSON.stringify(x)).join('\n')+'\n');
const info={source:src,registerCallCount:entries.length,uniqueIndices:direct.size,blockDefaults:defaults.size,outputRows:output.length,directRows:output.filter(x=>x.lookup==='explicit-map-index').length,defaultRows:output.filter(x=>x.lookup==='block-default-fill').length,fallbackRows:output.filter(x=>x.lookup==='getTag-index-0-fallback').length,propertyConstantsResolved:constantProps.size,unresolvedTargetPropertyExpressions:[...new Set(output.map(x=>x.targetPropertiesExpression).filter(x=>x&&Object.hasOwn(parseProps(x),'__unresolved_expression__')&&!constantProps.has(parseProps(x).__unresolved_expression__)))]};
fs.writeFileSync('.native-source/20261028/collision-next/dda-current-state-projection-20261029/extraction-summary.json',JSON.stringify(info,null,2)+'\n');
console.log(JSON.stringify(info,null,2));
