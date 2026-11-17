# Independent bounded source audit — ACCEPT

Pair: `1.11.2→1.12`. Candidate `fence-neighbor-face-collision.md` SHA-256 `3C5BB22D0700858739E0815FFC295C119AAB0787D846705C02FD9DB4835C6066` verified.

## Source identity

1.11.2 ready marker `CEABFB266993567AAB8C867525365E18AA0A0D7BA5A2CB7B6CFCC83D21318C40`; manifest `D908C2AF76598AD26999563BFEF0089F1D3C8C32483EEE6B108F0DEFA2773DF0`; provenance `DCDB65E1361297A584C5B93F581B1B539911911C6CEC1A203740BAA5379732C6`. 1.12 mapping-local provenance `27515E5F4C71E7DB2F035A81716507128D77466C75380AD1EF7D140C06401467`. Source rows match: FenceBlock `856CB92D4613289747A34A6BC85F306CA9D44B7057A45C79F2020D13025D1CF7` / `633AD3B20250BD010A28D0B1362B24C81A5508BE8A81A9D826953A3C9C4A703E`; FenceGateBlock `62CF70369634810D50137D846BE46F3901DEF38DF38EE0366DF2A0B06268BB67` / `38AE9E6AA1057A8E9B65FF78414D0D6A9A70CF2645F1EA42141FF2815AD6A477`; StairsBlock `5DF1782DA6EB7ACDB9935B3910A0484D6417661BFD6230ECF81DF0DE6D782627` / `C52A011A759B6F3EF6D45162D3B61F464CECEEE5D2D8735DE6F6E25A56E55611`.

## Verdict

The old predicate connects any fence gate without a directional face test. In 1.12, the neighbor's opposite queried face is passed to the face-shape predicate; a gate supplies `MIDDLE_POLE` only for the matching horizontal axis, and fence accepts that pole only for same-material fence or gate. Therefore the stated transverse-gate example removes an arm bit and changes the unchanged collision boxes. That geometry reaches local-player clipping through world collision collection and `Entity.move`. ACCEPT only for this bounded example. The candidate correctly leaves stair/state combinations unresolved: this is not an exhaustive neighbor crosswalk, pair coverage, pair freeze, or runtime/trajectory result.
