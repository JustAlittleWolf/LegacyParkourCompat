# Independent bounded source audit — ACCEPT

Pair: `1.11.2→1.12`. Candidate `wall-neighbor-face-collision.md` SHA-256 `D9012E7C589F6A72A0716F17E19AB43A060772C8C1E5BD93DF371E5656AC1D8D` verified.

## Source identity

1.11.2 ready marker `CEABFB266993567AAB8C867525365E18AA0A0D7BA5A2CB7B6CFCC83D21318C40`; manifest `D908C2AF76598AD26999563BFEF0089F1D3C8C32483EEE6B108F0DEFA2773DF0`; provenance `DCDB65E1361297A584C5B93F581B1B539911911C6CEC1A203740BAA5379732C6`. 1.12 mapping-local provenance `27515E5F4C71E7DB2F035A81716507128D77466C75380AD1EF7D140C06401467`. Source rows match: WallBlock `17DA6D881365E4DCE5134F88CB9A9E5273D1FA1734812D13410DC6924A3ACBFD` / `7E4378C46E367C32DECA14475E5161B6E4223947D83153CE3E7BA5C8A0D18E50`; FenceGateBlock `62CF70369634810D50137D846BE46F3901DEF38DF38EE0366DF2A0B06268BB67` / `38AE9E6AA1057A8E9B65FF78414D0D6A9A70CF2645F1EA42141FF2815AD6A477`; StairsBlock `5DF1782DA6EB7ACDB9935B3910A0484D6417661BFD6230ECF81DF0DE6D782627` / `C52A011A759B6F3EF6D45162D3B61F464CECEEE5D2D8735DE6F6E25A56E55611`.

## Verdict

The old wall predicate accepts any gate. 1.12 classifies the queried opposite neighbor face; it accepts SOLID and MIDDLE_POLE_THICK, and accepts MIDDLE_POLE for gates. Gate MIDDLE_POLE is orientation-axis dependent, so the reported transverse gate may lose its connection. The unchanged wall box selection then changes collision geometry reaching player `Entity.move` through world collision collection. ACCEPT only for this bounded case. Stair and virtual wall state combinations remain unresolved; this is not exhaustive provider coverage, pair freeze, or runtime/trajectory evidence.
