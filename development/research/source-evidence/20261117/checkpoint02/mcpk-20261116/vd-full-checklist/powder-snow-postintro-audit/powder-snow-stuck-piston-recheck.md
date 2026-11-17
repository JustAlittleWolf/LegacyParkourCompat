# Stuck piston response draft recheck

Prior audit: `audit.md`. This recheck applies only to the revised scratch draft `build/mcpk-20261116/vd-full-checklist/powder-snow-postintro/drafts/powder-snow-stuck-piston-response.md` at SHA-256 `1baacb3c01ad34b9146137e1df6c3afcc7fb65e65b0aee3be22693fc957004d6`.

Evidence identity is unchanged from the prior audit: 1.17.1 Mojmap is ready with source manifest SHA-256 `93270d229acfb751bf56daf1e7be26ce3dcff26b29e94aa157de621405a3463b`; 26.2 unobfuscated is ready with source manifest SHA-256 `a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894`. Rehashed manifests and the paired `Entity.java` and `PowderSnowBlock.java` source files match the prior evidence identity and supplied hashes.tsv.

## Verdict: ACCEPT

The revision addresses both requested precision points: it states the `stuckSpeedMultiplier.lengthSqr() > 1.0E-7` consumption gate and explains that a piston vector returned as zero by `limitPistonMovement` exits `Entity.move` before the stuck gate in both versions. Exact source lines remain 1.17.1 `Entity.move` 539–550 and 26.2 `Entity.move` 719–735. The shared gate therefore bounds the claimed comparison; when reached, 1.17.1 multiplies for any mover, while 26.2 skips scaling for `MoverType.PISTON` but still clears the multiplier and zeros delta movement. The revised movement-meaning sentence also conditions the piston result on the limited vector reaching that gate.

No remaining defect in this bounded consumer claim was found. The endpoint comparison still does not establish the introducing release within the interval, complete pair coverage, or runtime parity.
