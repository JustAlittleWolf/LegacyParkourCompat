# Independent bounded source audit — ACCEPT

Pair: `1.11.2→1.12`. Candidate `liquid-flow-face-classification.md` SHA-256 `54839855489D9F07FAF9B8444B20832919FFA507E350201F43ED9B620B120B74` verified.

## Source identity

1.11.2 ready marker `CEABFB266993567AAB8C867525365E18AA0A0D7BA5A2CB7B6CFCC83D21318C40`; manifest `D908C2AF76598AD26999563BFEF0089F1D3C8C32483EEE6B108F0DEFA2773DF0`; provenance `DCDB65E1361297A584C5B93F581B1B539911911C6CEC1A203740BAA5379732C6`. 1.12 mapping-local provenance `27515E5F4C71E7DB2F035A81716507128D77466C75380AD1EF7D140C06401467`. Source rows match: LiquidBlock `C91618C8445DD488DD61F4A01AED0799DAE2FAAF3585BC6F2D5164891E988244` / `00F7995DD49A48BF97316E30B3D6E41D8EE1E65DD212F3DC64891883621C4260`; World `27CFAA5FF45C2D88C492FC5DAE3EA4A2BB4536FD64BFF8498A9A7D0CB82EFB58` / `F7AB7A34D456E720E7EE4F34FBD0EB10F7F96151596C8C615AB2EDEF05A884D8`; PlayerEntity `87FE94FA6CBF7ABA18B9A5E3401664439EB8EBA9958173DA8FBCD05CC7AD948B` / `6A9D5F420B13625195B149C0D9468CF1284D5EB7DC1B2A46CF01B952A27EE063`.

## Verdict

The bounded classification claim is supported: old side-face handling uses neighboring material solidness except same-liquid/ice; new handling excludes attachment exceptions and stairs and requires SOLID face shape. Upward and same-liquid gates are as stated. For current state `LEVEL >= 8`, `getFlow` checks adjacent and above-adjacent side faces; a qualifying face adds `(0,-6,0)` before normalization. `World.applyLiquidDrag` applies normalized flow at `0.014` when liquid collision is enabled. The player's ordinary water collision path calls it, and PlayerEntity enables it when not flying. ACCEPT for this conditional current-state player response only. Fluid ticks/scheduling, rendering and state production are not established; no runtime/trajectory or pair-completeness claim.
