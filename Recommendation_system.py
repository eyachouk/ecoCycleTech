from fastapi import FastAPI, Request
from transformers import AutoTokenizer, AutoModel
import torch
import pandas as pd
from sklearn.metrics.pairwise import cosine_similarity

app = FastAPI()

# Load pre-trained model and tokenizer once at startup
tokenizer = AutoTokenizer.from_pretrained("sentence-transformers/all-MiniLM-L6-v2")
model = AutoModel.from_pretrained("sentence-transformers/all-MiniLM-L6-v2")
from fastapi.middleware.cors import CORSMiddleware

app = FastAPI()

origins = [
    "http://localhost:4200",  # Angular frontend origin
    "http://localhost",
    "http://localhost:8000",
]

app.add_middleware(
    CORSMiddleware,
    allow_origins=origins,
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)
data = {
    "name": [
        "Frigidaire Yasmine",
        "Téléphone Yasmine",
        "Télévision Yasmine",
        "Machine à laver Yasmine",
        "Climatiseur Yasmine"
    ],
    "description": [
        "Un réfrigérateur moderne et économique",
        "Un téléphone performant avec une bonne batterie",
        "Une télévision HD avec écran plat",
        "Machine à laver efficace et silencieuse",
        "Climatiseur puissant pour grandes pièces"
    ]
}
df = pd.DataFrame(data)

def compute_embeddings(texts):
    """Compute sentence embeddings using mean pooling."""
    tokens = tokenizer(texts, padding=True, truncation=True, return_tensors="pt")
    with torch.no_grad():
        outputs = model(**tokens)
        embeddings = outputs.last_hidden_state.mean(dim=1)
    return embeddings.numpy()

# Precompute embeddings for all items once
item_embeddings = compute_embeddings(df["description"].tolist())

def get_recommendations(query, top_k=3):
    """Return top_k recommendations for the query."""
    query_embedding = compute_embeddings([query])
    scores = cosine_similarity(query_embedding, item_embeddings).flatten()
    df["score"] = scores
    top_recs = df.sort_values(by="score", ascending=False).head(top_k)
    return top_recs[["name", "score"]].to_dict(orient="records")

@app.post("/recommend")
async def recommend(request: Request):
    body = await request.json()
    query = body.get("query", "").strip()
    if not query:
        return {"error": "Query parameter is required."}

    recommendations = get_recommendations(query)
    if not recommendations:
        return {"message": "No recommendations found for your query."}
    return recommendations

@app.get("/")
async def root():
    return {"message": "Welcome to the recommendation system"}

if __name__ == "__main__":
    import uvicorn
    uvicorn.run(app, host="0.0.0.0", port=8000)
