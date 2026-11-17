# Independent audit: 1.21.11 → 26.1.2 post-move block callbacks

Verdict: **ACCEPT** the bounded `NO_DIFF` report.

Candidate revision audited: `build/later-source-20261116/1.21.11--26.1.2/postmove_callbacks/report.md`, SHA-256 `5E62706EBE55568078A39966A405843E34DAFC135433A70844ED91F24FCE16FD`.

## Exact source identity

The ready markers identify 1.21.11 Mojmap and 26.1.2 unobfuscated sources. Their source-manifest and artifact-manifest file hashes match the marker digests: 1.21.11 `0c4d83fef84c101d9db88f331acec7960f3c8346228c28995e6cf57bbfcae555` / `c8210b15012dcc4e109c2f73015b4117abec1f8ab04fdd375dbc11419e9e246c`; 26.1.2 `54ae320660ca911a6d20514c965c33eb414fbedd1ad6727bbaa95fe906aa23c0` / `89f648229de83b0109460695c9f1f3bed7ef36efb84ede9224e80cce87118f92`. The 22 source files checked for this audit (the report's nine cited files plus `Block.java` and `SlimeBlock.java` at both endpoints) match their source-manifest SHA-256 entries.

## Audit result and evidence

I independently traced the local-player dispatch into `LivingEntity.aiStep`, the client-authoritative `applyEffectsFromBlocks()` gate, movement-record collection, the inside-block segment scan and callback/effect collector. The old/new endpoint bodies preserve the same local-player route, gate, scan order and callback flushing behavior. The cited endpoint file hashes in the candidate agree with canonical source files and the manifests.

The report's direct movement callback comparison is supported for bubble-column motion, web slowdown, powder snow, sweet berry bushes and honey. I also checked the path's separate ground `stepOn` call so it is not an omitted movement writer: `Block.stepOn` remains empty and `SlimeBlock.stepOn` retains the same low-vertical-speed horizontal multiplier and careful-step gate in both endpoints. Other listed `stepOn` overrides do not write player motion in this path; magma damage is outside the movement scope. `Entity.onInsideBlock` is empty for the local player; endpoint overrides found are for `ServerPlayer` and thrown ender pearls, not this route. Bed bounce is a fall-response path, not this post-move callback dispatch, and bed-entry eligibility remains excluded.

The 26.1.2 `applyEffectsFromBlocksForLastMovements()` addition remains unreachable from the local-player `LivingEntity.aiStep` route; its call site is `ItemEntity`, so its presence does not alter this slice. This acceptance is limited to the report's assigned post-move block-contact callback slice and is not pair closure.

## Limits

No product code, backlog, wiki inventory, Git history, build, test, runtime, or pair-coverage closure was inspected or changed. No implementation or trajectory claim is made beyond the compared source behavior.
