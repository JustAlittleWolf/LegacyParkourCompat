# Source-pair import and resume ledger

Checkpoint date: 2026-10-07. Integration branch: `feat/movement-completeness-integration`.

This ledger preserves each committed pair-owner report at its exact branch tip. Only pair-scoped files under the report folder are imported; shared workflow/checker changes are excluded. `active` and `partial` reports remain incomplete. Independent review, source freeze, implementation reconciliation, wiki disposition, and runtime parity remain separate statuses. Continue from the linked `run.md` `Resume checkpoint`; no uncommitted owner changes were copied.

| Pair / resume path | Owner tip | run.md blob | Report status | Files | Integration checkpoint |
|---|---|---|---|---:|---|
| [1.10.2--1.11.2](1.10.2--1.11.2/run.md) | feat/source-discovery-movement-source-1-10-2-1-11-2 @ ff5b16cfc06ba697be04503b786937d4058c45c2 | 61b5bfdb2fc5303e05f12d4aff95cfa86571cf4a | partial | 6 | imported from committed tip; see Resume checkpoint in run.md |
| [1.11.2--1.12.2](1.11.2--1.12.2/run.md) | feat/source-discovery-movement-source-1-11-2-1-12-2 @ 9c57f6d4ddc5aa75a6b4e7cf9347a26864e2f7ed | ad05139e5edffaa8cba8ae2abb7198ae65126a6d | active | 2 | imported from committed tip; see Resume checkpoint in run.md |
| [1.12.2--1.13.2](1.12.2--1.13.2/run.md) | feat/source-discovery-movement-source-1-12-2-1-13-2 @ ba6e847a56e3e3ec0da0cb82dc5b087e40b484eb | f7c85e4c82018fe60a772c24538e58d1bcc70dc5 | active | 16 | imported from committed tip; see Resume checkpoint in run.md |
| [1.13.2--1.14.4](1.13.2--1.14.4/run.md) | feat/source-discovery-movement-source-1-13-2-1-14-4 @ f567f09f2279fc0b79234cb6706abf729db89f9b | f8bd380d488a7bf66880834c7c1ae957ca9ba3d3 | partial | 17 | F002 snapshot accepted by blind review 10aa24e; pair remains active/incomplete; see Resume checkpoint in run.md |
| [1.14.4--1.15.2](1.14.4--1.15.2/run.md) | feat/source-discovery-movement-source-1-14-4-1-15-2 @ 1d776787edfd53b5230d51b012454cb4e4921d3d | c29d3516fb3e47f852947630edaca745c3fa1911 | partial | 6 | imported from committed tip; see Resume checkpoint in run.md |
| [1.15.2--1.16.5](1.15.2--1.16.5/run.md) | feat/source-discovery-movement-source-1-15-2-1-16-5 @ c0de66713b64e292dc51fabeeac1516ed269053d | 34695dcf523d1e1e2eadc51e37b25316a1b62f96 | partial | 10 | imported from committed tip; see Resume checkpoint in run.md |
| [1.16.5--1.17.1](1.16.5--1.17.1/run.md) | feat/source-discovery-movement-source-1-16-5-1-17-1 @ cea2c24ec75d3018594598fb14cf785be427e677 | e4f7c3e9b9489110f16b09136f0fb36de188fa6f | active | 1 | imported from committed tip; see Resume checkpoint in run.md |
| [1.17.1--1.18.2](1.17.1--1.18.2/run.md) | feat/source-discovery-movement-source-1-17-1-1-18-2 @ 7813662bf933ede80b681735fd494180141d6dac | bddc2cd38169f7e335f36e50961cf35a743ecda2 | partial | 8 | imported from committed tip; see Resume checkpoint in run.md |
| [1.18.2--1.19.2](1.18.2--1.19.2/run.md) | feat/source-discovery-movement-source-1-18-2-1-19-2 @ adc3ae8296d8074d6e3760550cf2b4d5dd7ca741 | e6555731a61efaf96b445be00c904d14d0aaa268 | active | 5 | imported from committed tip; see Resume checkpoint in run.md |
| [1.19.2--1.19.3](1.19.2--1.19.3/run.md) | feat/source-discovery-movement-source-1-19-2-1-19-3 @ 8e2c052b729d276f4f1e804e55d8253335ceebc4 | edd864117f88d6a019d9c6e03de403f07d1d57da | partial | 5 | imported from committed tip; see Resume checkpoint in run.md |
| [1.19.3--1.19.4](1.19.3--1.19.4/run.md) | feat/source-discovery-movement-source-1-19-3-1-19-4 @ 8e298c93ca88978f7dcfb51025b33dc2e26dae73 | 8bcd1b8c2542f626165ecdc37fe9c48b71541c8c | active | 5 | imported from committed tip; see Resume checkpoint in run.md |
| [1.19.4--1.20.1](1.19.4--1.20.1/run.md) | feat/source-discovery-movement-source-1-19-4-1-20-1 @ 3cd4e368b8fd1d4216428781257c23e927d34c62 | 3290fb44ba6ed9af142f2ea0eb8d27f6f9816ba3 | active | 6 | imported from committed tip; see Resume checkpoint in run.md |
| [1.20.1--1.20.2](1.20.1--1.20.2/run.md) | feat/source-discovery-movement-source-1-20-1-1-20-2 @ `ab68e6fbec162624c2aadbc6a2e04e3e36f2ea8a` | `6ff2e39221c34248402d84c4b5835d66f0725599` | active | 6 | refreshed from committed tip; diode support-removal evidence is scope-excluded because block-state evolution stays vanilla |
| [1.20.2--1.20.4](1.20.2--1.20.4/run.md) | feat/source-discovery-movement-source-1-20-2-1-20-4 @ 0881122019f1698daa6309daa6d2d96f8c247abc | eb02f8aa30a58bf4d6c2c13a78e4b2ab4153a4d3 | active | 1 | imported from committed tip; see Resume checkpoint in run.md |
| [1.20.4--1.20.6](1.20.4--1.20.6/run.md) | feat/source-discovery-movement-source-1-20-4-1-20-6 @ 51bb098e0afdaabee9171986e0b070482201ea69 | 43ef8dfb5f2690d5f151eafdd398dc727e8f4558 | partial | 7 | imported from committed tip; see Resume checkpoint in run.md |
| [1.20.6--1.21.1](1.20.6--1.21.1/run.md) | feat/source-discovery-movement-source-1-20-6-1-21-1 @ 77aa0de22c7abe244a506d6f775eb832e7536b62 | 4333cfabfaa7651f3a57c104481f56777485a4e2 | active | 3 | imported from committed tip; see Resume checkpoint in run.md |
| [1.21.1--1.21.3](1.21.1--1.21.3/run.md) | feat/source-discovery-movement-source-1-21-1-1-21-3 @ f92caefe9673791899f4fcc7dfa2ac27ea6a407b | ffbcb88d900de85288b6439d72f2daf97d1cdc3b | active | 2 | imported from committed tip; see Resume checkpoint in run.md |
| [1.21.10--1.21.11](1.21.10--1.21.11/run.md) | feat/source-discovery-movement-source-1-21-10-1-21-11 @ a98a196a181d83cd1e6a018c08efb216a8c3da75 | 31caddf67cb16318761f50e42e52627509541353 | partial | 4 | imported from committed tip; see Resume checkpoint in run.md |
| [1.21.11--26.1.2](1.21.11--26.1.2/run.md) | feat/source-discovery-movement-source-1-21-11-26-1-2 @ 7492e7af7c3312d3108b64f0abf235767ce62551 | 977b076a9d3ec2c32d6760ecbddc315e68cdb04a | active | 1 | imported from committed tip; see Resume checkpoint in run.md |
| [1.21.3--1.21.4](1.21.3--1.21.4/run.md) | feat/source-discovery-movement-source-1-21-3-1-21-4 @ f9de0b781d098689f1d129e8fec48edf24919e9f | e36a29ebc97ec4c2edca145c1df390870b12963d | active | 5 | imported from committed tip; see Resume checkpoint in run.md |
| [1.21.4--1.21.5](1.21.4--1.21.5/run.md) | feat/source-discovery-movement-source-1-21-4-1-21-5 @ a157cd1d904b2f695a287dfefc0abef92df3e615 | 9b71de1256fc71da2fee6e6a58620d120a53ed09 | active | 11 | imported from committed tip; see Resume checkpoint in run.md |
| [1.21.5--1.21.8](1.21.5--1.21.8/run.md) | feat/source-discovery-movement-source-1-21-5-1-21-8 @ 4bab4a06fcbc7c2557742ca47bf5649f488c2562 | b44cf84e0ed04a60bfd94b19fef50fd6ffaa1963 | partial | 8 | imported from committed tip; see Resume checkpoint in run.md |
| [1.21.8--1.21.10](1.21.8--1.21.10/run.md) | feat/source-discovery-movement-source-1-21-8-1-21-10 @ 95a9b6fc07d56c18145b525042cea4269d300f09 | 8c5cf842fbe1f47fd7f1a873d90b953f41336d27 | active | 1 | imported from committed tip; see Resume checkpoint in run.md |
| [1.8.9--1.9.4](1.8.9--1.9.4/run.md) | feat/source-discovery-movement-source-1-8-9-1-9-4 @ 1dbafbb7899c57ddf1718e421aa2c32e283fc7c7 | e58304e7964ae63e24073998950daacf5e3eb0fb | active | 10 | imported from committed tip; see Resume checkpoint in run.md |
| [1.9.4--1.10.2](1.9.4--1.10.2/run.md) | feat/source-discovery-movement-source-1-9-4-1-10-2 @ d2db57ca31a121dca9cc285f19d241d01e7c330c | 55ac034762bf47aa083c244c9ed8e08941c3ac0f | active | 4 | imported from committed tip; see Resume checkpoint in run.md |
| [26.1.2--26.2](26.1.2--26.2/run.md) | feat/source-discovery-movement-source-26-1-2-26-2 @ f8b2a85a7548c3635a4c00d9b3b0d62fbff3bfc7 | c7a5ddb0522949fde6bbfa7dfca9d370df1fbee7 | active | 1 | imported from committed tip; see Resume checkpoint in run.md |

Imported committed pair folders: 26 / 26. Owner worktrees dirty at capture: 1; uncommitted edits were excluded.
A later read-only worktree check found newer uncommitted edits, excluded from these committed imports, in:
- `feat/source-discovery-movement-source-1-18-2-1-19-2`: `1.18.2--1.19.2/run.md`.
- `feat/source-discovery-movement-source-1-21-4-1-21-5`: `1.21.4--1.21.5/run.md`, findings `F-02` and `F-03`, and untracked finding `F-11-powder-snow-wall-jump-state.md`.
- `feat/source-discovery-movement-source-1-21-5-1-21-8`: `1.21.5--1.21.8/run.md` and untracked finding `F-SHULKER-BULLET-PLAYER-LEVITATION.md`.

The imported report bytes remain pinned to the owner commit/blob IDs in this ledger. Re-import only after those edits are committed, then record the newer tip and status.

Do not infer pair completion or implementation readiness from a partial report, finding label, or source-folder import. The exact next source methods/ranges and remaining dependencies are in each pair run report. Preserve source-review and wiki lanes separately.
