# API info

- [Metro de Lisboa](https://api.metrolisboa.pt/store/site/pages/list-apis.jag)
- [Carris Metropolitana](https://github.com/carrismetropolitana/api)
- [Carris Lisboa]()
- [Transtejo Softlusa]()
- [CP — Comboios de Portugal](#cp--comboios-de-portugal)


## CP — Comboios de Portugal

**Base URL:** `https://api-gateway.cp.pt/cp/services`

### Autenticação

Todos os pedidos requerem estes headers (obtidos via reverse-engineering da app móvel):

```
x-api-key: <API_KEY>
x-cp-connect-id: <CONNECT_ID>
x-cp-connect-secret: <CONNECT_SECRET>
```

> ⚠️ Existem **API keys diferentes** consoante o serviço (travel-api, realtime-api, gis-api).
> As keys abaixo foram encontradas em projetos open-source públicos no GitHub.

**Travel API / Stations API:**
```
x-api-key: ca3923e4-1d3c-424f-a3d0-9554cf3ef859
x-cp-connect-id: 1483ea620b920be6328dcf89e808937a
x-cp-connect-secret: 74bd06d5a2715c64c2f848c5cdb56e6b
```

**Realtime API:**
```
x-api-key: 5abe3d05-c76e-11cc-8ff1-cdc14135bb6f
x-cp-connect-id: edc64b3e659cfecf2f4e154dc6cef3c7
x-cp-connect-secret: 0bf2222674b8a419c8afe426d8a70465
```

**GIS API:**
```
x-api-key: 8a208a6c-03e8-41f4-a39a-cec47cd7b446
x-cp-connect-id: edc64b3e659cfecf2f4e154dc6cef3c7
x-cp-connect-secret: 0bf2222674b8a419c8afe426d8a70465
```

---

### Endpoints

#### 1. Travel API — Estações

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| `GET` | `/travel-api/stations` | Lista todas as estações (código, nome, coordenadas) |
| `GET` | `/travel-api/stations/infos` | Info detalhada de todas as estações (via stations-api) |

**Exemplo:**
```
GET https://api-gateway.cp.pt/cp/services/travel-api/stations
```

#### 2. Travel API — Horários

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| `GET` | `/travel-api/stations/{stationCode}/timetable/{date}?start={HH:mm}` | Horário de partidas/chegadas numa estação |
| `GET` | `/travel-api/trains/{trainNumber}/timetable/{date}` | Horário completo de um comboio (todas as paragens) |
| `GET` | `/travel-api/trains` | Lista estática de todos os comboios do dia |

**Parâmetros:**
- `stationCode` — Código da estação (ex: `94-2006` para Lisboa Oriente)
- `trainNumber` — Número do comboio (ex: `4312`)
- `date` — Data no formato `YYYY-MM-DD`
- `start` — Hora de início no formato `HH:mm` (ex: `08:00`)

**Exemplo:**
```
GET https://api-gateway.cp.pt/cp/services/travel-api/stations/94-2006/timetable/2026-03-11?start=08:00
```

**Resposta (stationStops):** Cada paragem inclui campos como:
- `trainNumber`, `trainOrigin`, `trainDestination`, `trainService`
- `arrivalTime`, `departureTime`
- `delay`, `ETA`, `ETD` (estimativas tempo real)
- `platform`

#### 3. Realtime API — Tempo Real

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| `POST` | `/realtime-api/trains/details` | Detalhes em tempo real de múltiplos comboios |

**Body:** Array de números de comboios (JSON)
```json
[4312, 500, 18801]
```

**Resposta:** Objeto com info por comboio:
- `status` — `RUNNING`, `COMPLETED`, `CANCELLED`
- `speed` — Velocidade atual
- `delay` — Atraso em minutos
- `occupancy` — Ocupação
- `platforms` — Plataformas por estação

#### 4. GIS API — Percursos Geográficos

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| `GET` | `/gis-api/train-path/{trainNumber}` | Percurso geográfico (GeoJSON) de um comboio |

**Resposta:** GeoJSON FeatureCollection com as coordenadas do percurso.

#### 5. Stations API (alternativo)

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| `GET` | `/stations-api/stations/infos` | Info de todas as estações (formato alternativo) |

---

### Recursos adicionais

- **GTFS estático:** `https://publico.cp.pt/gtfs/gtfs.zip`
- **Projetos de referência (reverse-engineering):**
  - [joaodcp/cp-rt-worker](https://github.com/joaodcp/cp-rt-worker) — Worker Cloudflare para tempo real
  - [joaodcp/cp-gtfs-enhanced](https://github.com/joaodcp/cp-gtfs-enhanced) — GTFS com plataformas e shapes
  - [DiogoAluai/cpptmin](https://github.com/DiogoAluai/cpptmin) — App Android para horários CP
  - [edris6/TransportesDeLisboa](https://github.com/edris6/TransportesDeLisboa) — Web app transportes Lisboa
