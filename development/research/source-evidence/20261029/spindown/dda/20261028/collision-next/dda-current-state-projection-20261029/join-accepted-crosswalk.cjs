const fs=require('node:fs'),path=require('node:path');
const root=path.resolve('.native-source/20261028/collision-next/dda-current-state-projection-20261029');
const xroot=path.resolve('.native-source/20261028/collision-next/dda-first-hit-crosswalk-20261029');
function tsv(p){const lines=fs.readFileSync(p,'utf8').trimEnd().split(/\r?\n/);const cols=lines.shift().split('\t').map(x=>x.replace(/^"|"$/g,''));return lines.map(l=>{const a=l.split('\t').map(x=>x.replace(/^"|"$/g,'').replaceAll('""','"'));return Object.fromEntries(cols.map((c,i)=>[c,a[i]??'']));});}
const cross=[...tsv(path.join(xroot,'range-000-063/crosswalk.tsv')),...tsv(path.join(xroot,'range-064-131/crosswalk-corrected.tsv')),...tsv(path.join(xroot,'range-132-197/crosswalk.tsv'))];
const projected=fs.readFileSync(path.join(root,'block-state-data-legacy-index-projection.jsonl'),'utf8').trimEnd().split(/\r?\n/).map(JSON.parse);
const byId=new Map(cross.map(r=>[Number(r.old_id),r]));
const ids=[];
for(let id=0;id<198;id++){
 const c=byId.get(id), rows=projected.filter(r=>r.oldId===id), oldName=c?.old_key?.replace(/^minecraft:/,'');
 const aliases=rows.map(r=>r.legacyAliases||[]);
 const matches=aliases.map(as=>as.filter(a=>a.name?.replace(/^minecraft:/,'')===oldName));
 const present=matches.filter(x=>x.length).length;
 ids.push({oldId:id,oldKey:c?.old_key??null,metadataDomain:c?.metadata_domain??null,projectionRules:c?.projection_rules??null,metadataRows:rows.length,sameNameAliasMetadataRows:present,sameNameAliasCoverage:present===16?'all16':present?'partial':'none',sameNameAliasPropertyExamples:[...new Map(matches.flat().map(a=>[JSON.stringify(a.properties),a.properties])).values()].slice(0,6),status:present===16?'name-only match; exact 1.8.9 metadata-to-alias equivalence still requires source crosswalk':'OPEN: no complete same-name legacy alias join; numeric table row is not promoted'});
}
const out=path.join(root,'accepted-crosswalk-alias-join.json');fs.writeFileSync(out,JSON.stringify({inputCrosswalks:['range-000-063/crosswalk.tsv','range-064-131/crosswalk-corrected.tsv','range-132-197/crosswalk.tsv'],idCount:ids.length,rows:ids},null,2)+'\n');
console.log(JSON.stringify({ids:ids.length,nameOnlyAll16:ids.filter(x=>x.sameNameAliasCoverage==='all16').length,nameOnlyPartial:ids.filter(x=>x.sameNameAliasCoverage==='partial').length,noAlias:ids.filter(x=>x.sameNameAliasCoverage==='none').length,examples:ids.filter(x=>x.sameNameAliasCoverage==='all16').slice(0,8)},null,2));
