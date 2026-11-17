# Independent source audit verdict

**Verdict: ACCEPT**

Audited report: `build/mcpk-20261116/vd-full-checklist/correction-applicability-early-representatives/report.md`

Declared and recomputed report SHA-256: `986c3a2596b8d54b23d44ce51e448126c2d9774f6e1aa7e5ace0d38d53b9df1e` (match).

## Checks

- All 16 listed receiver source files were found at their reported paths. Each source file's byte SHA-256 matched the report and its local source-hash binding. For embedded-provenance trees, the `sources` entry matched and the provenance inputs identify the reported exact release. For ready-marker trees, version/mapping metadata, ready-to-manifest digest, and source-manifest file entry matched. The report's method declaration begins at each stated first line and the stated method range contains the body. This includes 1.14.1's mapping-local hidden provenance; no absent root marker was treated as a failure.
- Direct correction bodies from 1.8 through 1.15.2 have no vehicle/riding predicate or detach operation. The corresponding 1.16.1 body contains the stated `player.tickCount > 0 && player.getVehicle() != null` guard and `player.removeVehicle()` call at lines 726–727.
- The per-axis rules match the source in every listed sample: a relative position adds the packet offset to the current coordinate and leaves that axis's existing velocity component intact; an absolute position selects the packet coordinate and zeros only that axis's velocity component. This is explicit in the 1.8 receiver at lines 533–548, the 1.14.1 receiver at 650–678, and the Mojmap receivers at 667–695 (1.14.4), 673–716 (1.15.2), and 686–734 (1.16.1); the remaining listed bodies show the same per-axis branches and zero assignments.

## Boundary

Acceptance covers only the individually named releases in the audited report: 1.8, 1.8.4, 1.8.8, 1.9.2, 1.10, 1.10.2, 1.11, 1.11.1, 1.11.2, 1.13, 1.14, 1.14.1, 1.14.4, 1.15.1, 1.15.2, and 1.16.1. It does not infer behavior for unsampled releases or an exact introduction boundary between samples, and it does not externally authenticate upstream artifacts.
