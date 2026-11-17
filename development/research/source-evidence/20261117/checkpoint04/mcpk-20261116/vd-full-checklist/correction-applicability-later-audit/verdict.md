# Independent blind source audit: direct position-correction applicability (later representatives)

**Verdict: ACCEPT**

Audited report: `build/mcpk-20261116/vd-full-checklist/correction-applicability-later-representatives/report.md`

Frozen report SHA-256: `c5c4468473218422055649d3c6fda7487b26ee786a37ae9d5fe10cfe613a49e7` (recomputed and matched).

## Checks

- Recomputed `ClientPacketListener.java` SHA-256 for 1.17, 1.17.1, 1.18.1, 1.18.2, 1.19.2, 1.19.3, 1.20.1 and 1.20.4. Every hash matches the report and the corresponding local published identity record (manifest source row for sidecar releases; `sources` map entry for 1.17).
- Confirmed the exact method signatures and inclusive start/end lines in the report for all eight receivers.
- Confirmed that 1.17 through 1.19.3 conditionally call `removeVehicle()` only after `requestDismountVehicle()` returns true; no passenger/riding guard occurs in those direct receiver bodies. Confirmed that 1.20.1 and 1.20.4 have neither a direct dismount branch nor a direct vehicle-state check in the receiver.
- Confirmed each X/Y/Z position branch independently adds the packet delta to the current position and retains that axis's existing velocity component for a relative coordinate; the absolute branch takes the packet coordinate and sets that axis's velocity to `0.0`. The stated 1.17 and 1.20.1 X examples and the summarized rule across the sampled intermediate releases are supported.
- Confirmed the external source-manifest file hashes equal the ready marker `sourceManifestSha256` values and the report's listed digests. Confirmed release IDs in ready markers and provenance-file hashes for all sidecar releases. The 1.18.1 ready marker includes the reported provenance digest link; the other six sidecar ready markers omit it.
- Confirmed 1.17 uses the mapping-local `.decompile-provenance.json` format listed in `build/source-identities-20261116/locations.tsv`: its source map includes the receiver hash, and its recorded inputs include the 1.17 metadata JSON, client JAR and Mojang client mappings. There is no root ready marker or external source-manifest sidecar. The report states those limitations accurately.

## Scope

This ACCEPT covers the report's material source, line, behavior and local identity claims for the eight sampled releases only. It does not establish behavior for unsampled releases or make claims about product implementation, profiles, backlog, wiki, or runtime parity.
