# Independent source audit — support-position selector r2

## Verdict

**ACCEPT** the corrected bounded report, bound to `build/later-source-20261116/1.20.2--1.20.4/support_position_writer_revision_r2/coverage-r2.md`, SHA-256 `2236547FF531A4FA9D8886D3ED84067F266C4342BE059B6E3587684713AD4DC6`.

## Exact correction and evidence

The r2 report differs from the prior report only in the `Entity.getBlockJumpFactor` consumer sentence. I checked both exact Mojmap files and their ready-bound source-manifest rows. The sentence is now accurate: `getBlockJumpFactor` evaluates the in-entity block jump factor and selected-support block jump factor unconditionally, then returns the support value only when the in-entity factor equals `1.0`.

- 1.20.2 `Entity.java:819–822`: first lookup, second lookup, then conditional return.
- 1.20.4 `Entity.java:820–823`: same order and gate.
- Current `Entity.java` SHA-256 values match manifest rows: 1.20.2 `D7EE49AAEA5E862B92508E767562CABC8F10515605E8FB67565C8C8D5C23B01B`; 1.20.4 `07383522BFF169938136638EF8C3244CA511B56CA4266913524F99F9821331B9`.
- Exact ready/provenance/manifest sidecar identities match those bound by the prior audit: 1.20.2 ready `5FF3322F287C250EE914C7CA3840D053BFFC3C2C5F35E91B59314D948292483F`, provenance `1DAD9F725F6CB25B6568700FA0294F83B66EA9633B0D62ED354D7BD8B45D4F05`, source manifest `E439FF7E1D3F1D31E01BE1EDD141F94D917A9DC7FA0DE5F69904544CBBE62887`; 1.20.4 ready `6225B97979420CC743C8B7156A0CE512EF27D0DBF48BFFFBC40D3E1C8CB044D1`, provenance `10914D9B7B3FC29DE066CA24F7D905E4D4F0A451A6C81729A2D1981ADDFC1385`, source manifest `FD3C8668483E5FF208C847474F6CBD3952A909C4AA602ED98B24E3D11A9602A1`.

The prior independent audit (`support_position_writer_audit/verdict.md`) verified the unchanged selector body, six cited source-file hashes, and direct jump/speed/Soul Speed/friction consumer claims. Since r2 changes only the inaccurate lookup-versus-return wording, I reused those unchanged bounded checks and rechecked the changed sentence, its line anchors, current `Entity.java` bytes, and manifest rows.

## Limits

This accepts only the bounded read-side selector and direct consumer report. The writer/population of `mainSupportingBlockPos`, effective resource/tag membership, other movement operations, pair coverage, implementation applicability and runtime parity remain open. No product, wiki, backlog, Git-history, build, test, runtime, artifact or canonical-source work was performed.
