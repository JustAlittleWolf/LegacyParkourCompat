# Rebound independent verdict — report revision r1

## Immutable identities

- Prior report claim: `../lane-falling-water-fence-111-1111/report.md`, SHA-256 `D27497DBA3358C69C6364560887B196164FE4C62BED457765B6D569D17C52804`.
- Frozen audit roster: `source-roster.md`, SHA-256 `5F3F58CF6751E9432CCD5494CD2DB31620917778EA326BC859C6B96EE03EFF73`.
- Prior verdict: `verdict.md`, SHA-256 `514853E3EBC9199539F5B2CAB02134D220B0921A7A05262CD92EA37C5213E800`.
- Revised claim: `../lane-falling-water-fence-111-1111/report.corrected-r1.md`, SHA-256 `52F0B550A6F9D31E5E21E4AEF2D885C45DD6E30735B13E589D868DC9CC4611CE`; verified UTF-8 without BOM and LF-only line endings.
- Exact source manifests remain 1.11 `8B3E5DA58494F1DBA77410960753B0BF646425A1ADA491D8242CC71E6C7A59CA` and 1.11.1 `0D5F4697D4E4CE53B36D0DBFEC89D44830EB76753A6643B49E42F0857753066D`, Ornithe Feather.

## Scoped disposition

**ACCEPT** the same bounded no-difference claim for (1) the dry ordinary oak fence at either horizontal-face probe of falling water and (2) direct local-player reachability to the water-current velocity update. The r1 report now includes the area-loaded gate and correct release-specific `Entity.tick()` citations. Those amended details match the frozen exact sources:

- Local player tick gate: `LocalClientPlayerEntity.java:159-162` in both versions.
- Inherited tick to water check: 1.11 `Entity.java:313-319` and `374-375`; 1.11.1 `Entity.java:317-323` and `378-379`.
- `World.applyLiquidDrag()` area gate: 1.11 `World.java:1592-1601`; 1.11.1 `World.java:1571-1580`.
- `checkWaterCollisions()` and call into world: 1.11 `Entity.java:884-899`; 1.11.1 `Entity.java:934-950`.
- Velocity writes after loaded fluid scan: 1.11 `World.java:1607-1634`; 1.11.1 `World.java:1586-1613`.

No changed source claim undermines the prior acceptance. This remains a bounded endpoint result only; it does not establish full-pair coverage, runtime parity, implementation applicability beyond the named behavior, or an intermediate release boundary. No blocker.