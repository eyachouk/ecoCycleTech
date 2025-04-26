from transformers import AutoTokenizer, AutoModel
import pandas as pd
from sklearn.metrics.pairwise import cosine_similarity

# Load pre-trained model and tokenizer
tokenizer = AutoTokenizer.from_pretrained("sentence-transformers/all-MiniLM-L6-v2")
model = AutoModel.from_pretrained("sentence-transformers/all-MiniLM-L6-v2")

# Example dataset
data = {
    "name": ["Item A", "Item B", "Item C"],
    "description": ["Funny movie", "Action-packed thriller", "Romantic drama"]
}
df = pd.DataFrame(data)

# Compute embeddings for items
def compute_embeddings(texts):
    tokens = tokenizer(texts, padding=True, truncation=True, return_tensors="pt")
    embeddings = model(**tokens).last_hidden_state.mean(dim=1)
    return embeddings.detach().numpy()

item_embeddings = compute_embeddings(df["description"].tolist())

# Recommendation function
def get_recommendations(query):
    query_embedding = compute_embeddings([query])
    scores = cosine_similarity(query_embedding, item_embeddings).flatten()
    df["score"] = scores
    return df.sort_values(by="score", ascending=False).head()

# FastAPI server
from fastapi import FastAPI, Request

app = FastAPI()

@app.post("/recommend")
async def recommend(request: Request):
    body = await request.json()
    query = body.get("query", "")
    recommendations = get_recommendations(query)
    return recommendations[["name", "score"]].to_dict(orient="records")

if __name__ == "__main__":
    import uvicorn
    uvicorn.run(app, host="0.0.0.0", port=8000)
